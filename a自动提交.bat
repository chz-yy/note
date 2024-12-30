@echo off
echo Adding changes...
git status
git add .

echo Committing changes...
git commit -m "init"

echo Pushing changes to remote repository...
git push origin master:master

echo Done!
pause
