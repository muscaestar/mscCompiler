// function SimpleFunction.test 2(SimpleFunction.test)
@SP
A=M
M=0
@SP
M=M+1
@SP
A=M
M=0
@SP
M=M+1

// push local 0
@LCL
D=M
@0
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// push local 1
@LCL
D=M
@1
A=D+A
D=M
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

// not
@SP
M=M-1
A=M
D=M
M=!D
@SP
M=M+1

// push argument 0
@ARG
D=M
@0
A=D+A
D=M
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

// push argument 1
@ARG
D=M
@1
A=D+A
D=M
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

// return 
@LCL
D=M
@5
M=D
D=D-A
A=D
D=M
@6
M=D
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@5
D=M-1
A=D
D=M
@THAT
M=D
@5
D=M
@2
D=D-A
A=D
D=M
@THIS
M=D
@5
D=M
@3
D=D-A
A=D
D=M
@ARG
M=D
@5
D=M
@4
D=D-A
A=D
D=M
@LCL
M=D
@6
A=M
0;JMP

