# 后端导出Excel和PDF

```java
//controller
public ResultEntity exportHistoryAuditToExcel(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @ApiParam(name = "year", value = "年度信息", required = false)@RequestParam(value = "year",defaultValue ="", required = false) String year,
                                         @ApiParam(name = "month", value = "月度信息", required = false)@RequestParam(value = "month", defaultValue = "", required = false) String month,
                                         @ApiParam(name = "department", value = "部门", required = false)@RequestParam(value = "department", required = false) String department,
                                         @ApiParam(name = "uid", value = "教师工号", required = false)@RequestParam(value = "uid", required = false) String uid,
                                         @ApiParam(name = "name", value = "教师姓名", required = false)@RequestParam(value = "name", required = false) String name,
                                         @ApiParam(name = "status", value = "状态查询", required = false)@RequestParam(value = "status", required = false) String status){

        Page<HistoryAudit> page = new Page<>(pageNum, 10);         // 当前页为传入的参数，默认每页显示10条数据
        ByteArrayOutputStream outputStream =exportService.exportHistoryAuditToExcel(page, year, month, department, uid, name, status);
        byte[] excelBytes = outputStream.toByteArray();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=history_audit_" + year + "_" + month + ".xlsx");
//        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        return BasicResponseUtils.success(excelBytes);
    }
使用ResponseEntity的body返回二进制数组，前端无法接收，直接报错
后改为使用自定义实体，放入data字段，后端会自动将二进制数组转为base64编码字符串，前端接收到data再解码就可以下载文件了
```



```java
//serverice
public ByteArrayOutputStream exportHistoryAuditToExcel(Page<HistoryAudit> page, String year, String month, String department, String uid, String name, String status) {
        Page<HistoryAudit> historyAudit = historyAuditServicelmpl.findHistoryAudit(page, year, month, department, uid, name, status);
        List<HistoryAudit> records = historyAudit.getRecords();
        String fileName="history_audit_" + year + "_" + month;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CustomRowWriteHandler customRowWriteHandler = new CustomRowWriteHandler();
        customRowWriteHandler.setDate(year+"-"+month);
        try {
            EasyExcel.write(outputStream)
                    .registerWriteHandler(customRowWriteHandler)
                    .head(HistoryAudit.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet(fileName)
                    .doWrite(records);
            return outputStream;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
```

```java
//控制器
public class CustomRowWriteHandler implements RowWriteHandler {

    /**
     *  序号的样式，与其他列保持一样的样式
     */
    private CellStyle firstCellStyle;

    private static final String FIRST_CELL_NAME = "序号";
    private static final String Second_CELL_NAME = "日期";

    public void setDate(String date) {
        this.date = date;
    }

    private String date = "";

    /**
     * 列号
     */
    private int count = 0;

    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {
        // 每一行首列单元格
        Cell cell = row.createCell(0);
        if (firstCellStyle == null) {
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            firstCellStyle = CellStyle(workbook);
        }
        //设置列宽  0列 10个字符宽度
        writeSheetHolder.getSheet().setColumnWidth(0, 10 * 256);
        if (row.getRowNum() == 0) {
            cell.setCellValue(FIRST_CELL_NAME);
            cell.setCellStyle(firstCellStyle);
        }else {
            cell.setCellValue(++count);
        }

        Cell cell2 = row.createCell(1);

        //设置列宽  0列 10个字符宽度
        writeSheetHolder.getSheet().setColumnWidth(0, 10 * 256);
        if (row.getRowNum() == 0) {
            cell2.setCellValue(Second_CELL_NAME);
            cell2.setCellStyle(firstCellStyle);
            return;
        }
        cell2.setCellValue(date);
    }
    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {

    }

    public static CellStyle CellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 灰色
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        //设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        //文字
        Font font = workbook.createFont();
        font.setBold(Boolean.TRUE);
        cellStyle.setFont(font);
        return cellStyle;
    }
}
```

```java
//转换器
public class ExcelStatusConverterUtils implements Converter<String> {

    @Override
    public WriteCellData<String> convertToExcelData(String i, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        switch (i){
            case "0":return new WriteCellData<>("待审核");
            case "1":return new WriteCellData<>("已审核");
            case "2":return new WriteCellData<>("被驳回");
            case "3":return new WriteCellData<>("待提交");
            default:return new WriteCellData<>("");
        }
    }
}
```

```java
@ExcelProperty(value = "审核状态",index = 11,converter = ExcelStatusConverterUtils.class)
    @ColumnWidth(20)
    @TableField(value = "status")
    private String status;
```

```java
public class PdfTableUtils {

    private float fontWidth;
    private float fontHeight;
    private float pageHeight;
    private float pageWidth;
    private PDPageContentStream contentStream;
    private PDFont font;
    private float padding = 2f;
    private float pageMargin = 10f;
    private float fontSize = 14.0f;
    private String[] header;
    private int[] cellSpan;
    private PDPage page;
    private float courY;
    private float leading = 14f;
    private List<String[]> contexts;
    private float[] cellWidth;
    private float[] courXPosion;
    private PDDocument document;
    private PDRectangle rectangle;


    public PdfTableUtils(PDDocument document,PDRectangle rectangle, PDFont font, int[] cellSpan, String[] header, List<String[]> contexts) {
        this.document = document;
        this.rectangle=rectangle;

        this.page = new PDPage();
        this.page.setMediaBox(rectangle);

        try {
            this.contentStream = new PDPageContentStream(document, this.page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.document.addPage(this.page);


        this.font = font;
        this.pageWidth = this.page.getMediaBox().getWidth();
        this.pageHeight = this.page.getMediaBox().getHeight();
        this.fontHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000f * fontSize;
        this.fontWidth = font.getAverageFontWidth() / 1000f * fontSize;
        this.courY = pageHeight - pageMargin;
        this.header = header;
        this.cellSpan = cellSpan;
        this.contexts = contexts;

        courXPosion = new float[header.length + 1];
        cellWidth = new float[header.length];
        courXPosion[0] = pageMargin;
        for (int i = 0; i < cellSpan.length; i++) {
            float width = (pageWidth - pageMargin * 2) * (cellSpan[i] / 100f);
            courXPosion[i + 1] = courXPosion[i] + width;
            cellWidth[i] = width;
        }
        contexts.add(0, header);
    }
    //画表格
    public void createTable() throws IOException {
        //画第一条横线
        contentStream.moveTo(pageMargin, courY);
        contentStream.lineTo(pageWidth - pageMargin, courY);
        contentStream.stroke();
        //填充内容
        for (int i = 0; i < contexts.size(); i++) {
            courY = courY - padding - fontHeight;
            String[] rows = contexts.get(i);
            handleData(rows);
        }

        //统一画竖线  最后画就可以
        for (int i = 0; i < courXPosion.length; i++) {
            contentStream.moveTo(courXPosion[i], pageHeight - pageMargin);
            contentStream.lineTo(courXPosion[i], courY);
            contentStream.stroke();
        }
        contentStream.close();

    }
    //将一行数据写入，同时计算最大行数，然后画线
    public void handleData(String[] data) throws IOException {
        int maxLineNum = 1;
        float courX = pageMargin + padding;
        for (int i = 0; i < data.length; i++) {
            int lineNum = writeText(data[i], cellWidth[i] - padding * 2, courX);
            maxLineNum = lineNum > maxLineNum ? lineNum : maxLineNum;
            courX = courX + cellWidth[i];
        }
        courY = courY - padding - fontHeight * (maxLineNum - 1 ) - leading * (20f / 72f);
        //小于0代表超出页面,页面左下角为原点（0，0）
        if(courY<0){
            //画竖线
            for (int i = 0; i < courXPosion.length; i++) {
                contentStream.moveTo(courXPosion[i], pageHeight - pageMargin);
                contentStream.lineTo(courXPosion[i], courY);
                contentStream.stroke();
            }
            //添加一页
            this.courY = pageHeight - pageMargin;
            this.page = new PDPage();
            this.page.setMediaBox(rectangle);
            contentStream.close();
            this.contentStream = new PDPageContentStream(document, this.page);
            document.addPage(page);
        }
        //画横线
        contentStream.moveTo(pageMargin, courY);
        contentStream.lineTo(pageWidth - pageMargin, courY);
        contentStream.stroke();

    }
    //将字写入（x，coury）位置
    public int writeText(String text, float cellWidth, float x) throws IOException {
        int lineNum = (int) Math.floor(cellWidth / fontWidth);
        List<String> textList = handleText(text, lineNum);
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setLeading(leading);
        contentStream.newLineAtOffset(x, courY);
        for (int i = 0; i < textList.size(); i++) {
            contentStream.showText(textList.get(i));
            contentStream.newLine();
        }
        contentStream.endText();
        return textList.size();
    }

    //一行最多lineNum个字，将text分行
    public List<String> handleText(String text, int lineNum) {
        text = text.replace("\n", "");
        text = StringUtils.deleteWhitespace(text);
        List<String> list = new ArrayList<>();
        if(text.length()<lineNum){
            list.add(text);
            return list;
        }
        int count=0;
        int num=lineNum*2;
        StringBuilder temp= new StringBuilder();
        //如果是数字，行字数加1
        for(int i=0;i<text.length();i++){
            if(Character.isDigit(text.charAt(i))){
                count++;
            }else {
                count+=2;
            }
            temp.append(text.charAt(i));
            if(count==num-1&&i+1<text.length()&&!Character.isDigit(text.charAt(i+1))){
                count=0;
                list.add(temp.toString());
                temp.delete(0,temp.length());
            }
            if(count==num||i+1==text.length()){
                count=0;
                list.add(temp.toString());
                temp.delete(0,temp.length());
            }
        }
        return list;
    }
}
```

```java
public ByteArrayOutputStream exportHistoryAuditToPDF(Page<HistoryAudit> page, String year, String month, String department, String uid, String name, String status) {
        // Generate Excel file stream
        Page<HistoryAudit> historyAudit = historyAuditServicelmpl.findHistoryAudit(page, year, month, department, uid, name, status);
        List<HistoryAudit> records = historyAudit.getRecords();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //String filePath = "E:\\test.pdf";
        PDDocument document=new PDDocument();
        PDRectangle rectangle = new PDRectangle(792,612);

        try {
            //加载字体
            Resource resource = new ClassPathResource("/font/DENG.TTF");
            InputStream inputStream = resource.getInputStream();
            PDFont font = PDType0Font.load(document,inputStream);

            String[] header = new String[]{"序号", "日期", "工号", "姓名", "一级部门","二级部门","员工子组","岗位类别","缺勤类型","活跃天数","备注信息","审核状态"};
            int[] cellSpan = {6, 8, 8, 8, 9,9,9,9,9,8,9,8};
            List<String[]> listText=convertToList(records);
            PdfTableUtils table = new PdfTableUtils(document,rectangle, font, cellSpan, header, listText);
            table.createTable();

            document.save(outputStream);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputStream;
    }

    public List<String[]> convertToList(List<HistoryAudit> historyAudits){
        AtomicInteger i= new AtomicInteger(1);
        List<String[]> collect = historyAudits.stream().map(row -> {
            String status = "";
            switch (row.getStatus()) {
                case "0":
                    status = "待审核";
                    break;
                case "1":
                    status = "已审核";
                    break;
                case "2":
                    status = "被驳回";
                    break;
                case "3":
                    status = "待提交";
                    break;
                default:
                    break;
            }
            return new String[]{
                    String.valueOf(i.getAndIncrement()),
                    row.getYear().trim() + "-" + row.getMonth().trim(),
                    row.getUid().trim(),
                    row.getName(),
                    row.getDepartment(),
                    row.getSecondDepartment(),
                    row.getYGZZ(),
                    row.getGW(),
                    row.getType(),
                    row.getScore(),
                    row.getRemark(),
                    status
            };
        }).collect(Collectors.toList());

        return collect;
    }
```

