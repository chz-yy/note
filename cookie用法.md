# cookie用法

```js
function setCookie(name, value, expires) {
    let expiresText = "";
    if (expires) {
        const date = new Date(expires);
        expiresText = `expires=${date.toUTCString()};`;
    }
    document.cookie = `${name}=${value}; ${expiresText} path=/`;
}

// 设置有效期为当前时间加1小时
const expires = new Date(Date.now() + 1000 * 60 * 60);
setCookie('token', 'your-token-value', expires);
```

```js
function getCookie(name) {
    const nameEQ = `${name}=`;
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        let c = cookies[i];
        while (c.charAt(0) === ' ') c = c.substring(1);
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

const token = getCookie('token');
console.log('Token:', token);
```

