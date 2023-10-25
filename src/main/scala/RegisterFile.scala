import chisel3._
import chisel3.util._

class RegisterFile extends Module {
  val io = IO(new Bundle {
    val aSel = Input(UInt(3.W))  // width of 3 because we have only 8 registers.
    val bSel = Input(UInt(3.W))
    val writeData = Input(UInt(32.W))
    val writeSel = Input(UInt(3.W))
    val writeEnable = Input(Bool())

    val a = Output(UInt(32.W))
    val b = Output(UInt(32.W))
  })

  val registers = Vec(8, RegInit(0.U(32.W)))

  when(io.writeEnable) {
    registers(io.writeSel) := io.writeData
  }

  io.a := registers(io.aSel)
  io.b := registers(io.bSel)
}