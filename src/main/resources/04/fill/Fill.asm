// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
@R0
M=0				// R0 = 0
(LOOP)
	@KBD
	D=M			// D = RAM[KBD]

	@BLACK
	D;JNE		// if RAM[KBD] = 0, jump to BLACK

	(WHITE)
	@R0
	M=0			// R0 = 0

	@PRINT
	0;JMP

	(BLACK)
	@R0
	M=-1		// R0 = -1

	(PRINT)
	@SCREEN
	D=A			// D = SCREEN

	@address
	M=D			// address = SCREEN

	(WHILE)
		@KBD
		D=A		// D = KBD

		@address
		D=M-D	// D = address - KBD

		@LOOP
		D;JGE	// if address - KBD >= 0, jump to LOOP

		@R0
		D=M		// D = R0

		@address
		A=M
		M=D	// RAM[address] = R0

		@address
		M=M+1	// address = address + 1

		@WHILE
		0;JMP
