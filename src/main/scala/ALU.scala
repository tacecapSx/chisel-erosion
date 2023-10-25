import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    val a = Input(UInt(16.W))
    val b = Input(UInt(16.W))
    val fn = Input(Bool()) // Control input for the condition
    val y = Output(UInt(16.W))
  })

  // Perform addition
  val addition = io.a + io.b

  // Check the condition (x == 0)
  val eqZero = io.fn && (io.a === 0.U)

  // Output the result based on the condition
  io.y := Mux(eqZero, 0.U, addition)
}