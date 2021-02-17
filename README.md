# 依据基本原理构建现代计算机
这是[Nand to Tetris](https://www.nand2tetris.org)从硬件模拟到编译器所有的项目实现。
## 基本模块
1. [（代码）](https://github.com/muscaestar/mscCompiler/tree/master/src/main/resources) 用硬件描述语言,实现逻辑电路,组合实现寄存器,算术单元, CPU以及内存,最后在硬件模拟器上搭建出冯诺依曼结构计算机.
2. [（代码）](https://github.com/muscaestar/mscCompiler/tree/master/mscAssembler/src/main/java) Java编写的汇编器:将Hack计算机的汇编语言转换成二进制指令.
3. Java编写的双层编译器:将Jack(面向对象的高级语言)程序编译成虚拟机语言
   [（代码）](https://github.com/muscaestar/mscCompiler/tree/master/src/main/java) ,
   再将虚拟机指令编译为针对Hack计算机的汇编语言
   [（代码）](https://github.com/muscaestar/mscCompiler/tree/master/mscVMTranslator/src/main/java) .