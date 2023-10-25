import chisel3._
import chisel3.util._

class ControlUnit extends Module {
  val io = IO(new Bundle {
    val instruction = Input(UInt(32.W))
    val opcode = Output(UInt(4.W))
    val regA = Output(UInt(4.W))
    val regB = Output(UInt(4.W))
    val regC = Output(UInt(4.W))
    val immediate = Output(UInt(16.W))
    val aluOp = Output(Bool())
    val loadIm = Output(Bool())
    val loadBy = Output(Bool())
    val saveBy = Output(Bool())
    val branch = Output(Bool())
    val jump = Output(Bool())
    val end = Output(Bool())
  })
  
  val opcode = io.instruction(31, 28)
  val regA = io.instruction(27, 24)
  val regB = io.instruction(23, 20)
  val regC = io.instruction(19, 16)
  val immediate = io.instruction(15, 0)

  io.opcode := opcode
  io.regA := regA
  io.regB := regB
  io.regC := regC
  io.immediate := immediate
  io.aluOp := false.B
  io.loadIm := false.B
  io.loadBy := false.B
  io.saveBy := false.B
  io.branch := false.B
  io.jump := false.B
  io.end := false.B

  switch(opcode) {
    is("b0000".U) {
      // No operation (nop)
      io.end := true.B
    }
    is("b0001".U) {
      // Addition
      io.aluOp := true.B
    }
    is("b0010".U) {
      // Immediate addition
      io.aluOp := true.B
    }
    is("b0011".U) {
      // Load immediate
      io.loadIm := true.B
    }
    is("b0100".U) {
      // Load byte
      io.loadBy := true.B
    }
    is("b0101".U) {
      // Save byte
      io.saveBy := true.B
    }
    is("b0110".U) {
      // Branch if equals zero
      io.branch := true.B
    }
    is("b0111".U) {
      // Jump
      io.jump := true.B
    }
    is("b1000".U) {
      // End execution
      io.end := true.B
    }
  }
}