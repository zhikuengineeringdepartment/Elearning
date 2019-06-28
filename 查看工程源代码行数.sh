#查看vue工程文件里面某个文件夹下的源代码行数
cd /Users/zy/workspace-for-front-end/inspiration-notebook-mobile
number1=`find ./src -name "*.*" | xargs grep -v "^$" | wc -l`
output=`expr $number1`
echo "代码行数为 : $output"