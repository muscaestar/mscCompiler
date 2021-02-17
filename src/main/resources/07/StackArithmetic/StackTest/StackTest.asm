// push constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1

// push constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1

// eq
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@StackTest_BRANCH_0
D;JEQ
@SP
A=M
M=0
@StackTest_JUMP_0
0;JMP
(StackTest_BRANCH_0)
@SP
A=M
M=-1
(StackTest_JUMP_0)
@SP
M=M+1

// push constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1

// push constant 16
@16
D=A
@SP
A=M
M=D
@SP
M=M+1

// eq
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@StackTest_BRANCH_1
D;JEQ
@SP
A=M
M=0
@StackTest_JUMP_1
0;JMP
(StackTest_BRANCH_1)
@SP
A=M
M=-1
(StackTest_JUMP_1)
@SP
M=M+1

// push constant 16
@16
D=A
@SP
A=M
M=D
@SP
M=M+1

// push constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1

// eq
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@StackTest_BRANCH_2
D;JEQ
@SP
A=M
M=0
@StackTest_JUMP_2
0;JMP
(StackTest_BRANCH_2)
@SP
A=M
M=-1
(StackTest_JUMP_2)
@SP
M=M+1

// push constant 892
@892
D=A
@SP
A=M
M=D
@SP
M=M+1

// push constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1

// lt
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@StackTest_BRANCH_3
D;JLT
@SP
A=M
M=0
@StackTest_JUMP_3
0;JMP
(StackTest_BRANCH_3)
@SP
A=M
M=-1
(StackTest_JUMP_3)
@SP
M=M+1

// push constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1

// push constant 892
@892
D=A
@SP
A=M
M=D
@SP
M=M+1

// lt
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@StackTest_BRANCH_4
D;JLT
@SP
A=M
M=0
@StackTest_JUMP_4
0;JMP
(StackTest_BRANCH_4)
@SP
A=M
M=-1
(StackTest_JUMP_4)
@SP
M=M+1

// push constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1

// push constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1

// lt
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@StackTest_BRANCH_5
D;JLT
@SP
A=M
M=0
@StackTest_JUMP_5
0;JMP
(StackTest_BRANCH_5)
@SP
A=M
M=-1
(StackTest_JUMP_5)
@SP
M=M+1

// push constant 32767
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1

// push constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1

// gt
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@StackTest_BRANCH_6
D;JGT
@SP
A=M
M=0
@StackTest_JUMP_6
0;JMP
(StackTest_BRANCH_6)
@SP
A=M
M=-1
(StackTest_JUMP_6)
@SP
M=M+1

// push constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1

// push constant 32767
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1

// gt
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@StackTest_BRANCH_7
D;JGT
@SP
A=M
M=0
@StackTest_JUMP_7
0;JMP
(StackTest_BRANCH_7)
@SP
A=M
M=-1
(StackTest_JUMP_7)
@SP
M=M+1

// push constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1

// push constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1

// gt
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@StackTest_BRANCH_8
D;JGT
@SP
A=M
M=0
@StackTest_JUMP_8
0;JMP
(StackTest_BRANCH_8)
@SP
A=M
M=-1
(StackTest_JUMP_8)
@SP
M=M+1

// push constant 57
@57
D=A
@SP
A=M
M=D
@SP
M=M+1

// push constant 31
@31
D=A
@SP
A=M
M=D
@SP
M=M+1

// push constant 53
@53
D=A
@SP
A=M
M=D
@SP
M=M+1

// add
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M+D
@SP
M=M+1

// push constant 112
@112
D=A
@SP
A=M
M=D
@SP
M=M+1

// sub
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M-D
@SP
M=M+1

// neg
@SP
M=M-1
A=M
D=M
M=-D
@SP
M=M+1

// and
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M&D
@SP
M=M+1

// push constant 82
@82
D=A
@SP
A=M
M=D
@SP
M=M+1

// or
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M|D
@SP
M=M+1

// not
@SP
M=M-1
A=M
D=M
M=!D
@SP
M=M+1

