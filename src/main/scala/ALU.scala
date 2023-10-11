import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
  })

  //Implement this module here
  /*
    HDL Implementation
    
    CHIP ALU {
      IN
          x[16], y[16], // 16-bit inputs
          zx,           // zero the x input?
          nx,           // negate the x input?
          zy,           // zero the y input?
          ny,           // negate the y input?
          f,            // compute out = x + y (if 1) or x & y (if 0)
          no;           // negate the out output?

      OUT
          out[16], // 16-bit output
          zr,      // 1 if (out == 0), 0 otherwise
          ng;      // 1 if (out < 0),  0 otherwise

      PARTS:
      Mux16(a=x,b[0..15]=false,sel=zx,out=zeroX); //zero x
      Not16(in=zeroX,out=notX);
      Mux16(a=zeroX,b=notX,sel=nx,out=finalX); //choose either zeroed x or negated x
      
      Mux16(a=y,b[0..15]=false,sel=zy,out=zeroY); //zero y
      Not16(in=zeroY,out=notY);
      Mux16(a=zeroY,b=notY,sel=ny,out=finalY); //choose either zeroed y or negated y
      
      Add16(a=finalX,b=finalY,out=addRes);
      And16(a=finalX,b=finalY,out=andRes);
      Mux16(a=andRes,b=addRes,sel=f,out=functionRes); //choose which function to perform
      
      Not16(in=functionRes,out=notFunctionRes);
      Mux16(a=functionRes,b=notFunctionRes,sel=no,out=out); //choose normal or negated function result
          Mux16(a=functionRes,b=notFunctionRes,sel=no,out[0..7]=fFunctionResL,out[8..15]=fFunctionResR,out[15]=ng);

      //check if output == 0
      Or8Way(in=fFunctionResL,out=check1);
      Or8Way(in=fFunctionResR,out=check2);
      Or(a=check1,b=check2,out=zrTemp);
      Not(in=zrTemp,out=zr);
  }
  */
}