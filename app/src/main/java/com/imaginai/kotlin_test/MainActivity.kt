package com.imaginai.kotlin_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imaginai.kotlin_test.databinding.ActivityMainBinding
import java.text.DecimalFormat


/* This calculator is only capable to handle 1 operation at once (Example: 4+4)
 Typing operations like 4+5+2-2 are handled, user won't be able to type even has been tried by user.

 To extend its operation capabilities, for example to execute multi operations in one time 4+4-2/2*4,
 an eval() function could be used to develop this in very short time
 eval() function : (https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/eval)

 Due to UI design using eval() function won't be a security problem (SQl injection, Remote Code Execution or XSS)
 but since this is an course project and aim is to learn android programming I choosed to code it myself.

 Due to that is my first project on android this project is not SOLID written nor OOP.

 @Author Ismail Tamer TÜRKER | turkertamer41@gmail.com
*/


class MainActivity : AppCompatActivity() {

    //The base string that the program is iterate on it and then will transform it to "double" "math_operation" "double" format to complete the calculation
    var Expression = ""

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




        //Click Listeners for number buttons
         binding.button0.setOnClickListener() {

             //If statment is standing for to prevent typing 0 as first number
             if(Expression.isNotEmpty()) {

                 Expression = Expression + "0"
             binding.Result.text = Expression
         }  }

        binding.button1.setOnClickListener() {
            Expression = Expression + "1"
            binding.Result.text = Expression
        }
        binding.button2.setOnClickListener() {
            Expression = Expression + "2"
            binding.Result.text = Expression
        }
        binding.button3.setOnClickListener() {
            Expression = Expression + "3"
            binding.Result.text = Expression
        }
        binding.button4.setOnClickListener() {
            Expression = Expression + "4"
            binding.Result.text = Expression
        }
        binding.button5.setOnClickListener() {
            Expression = Expression + "5"
            binding.Result.text = Expression
        }
        binding.button6.setOnClickListener() {
            Expression = Expression + "6"
            binding.Result.text = Expression
        }
        binding.button7.setOnClickListener() {
            Expression = Expression + "7"
            binding.Result.text = Expression
        }
        binding.button8.setOnClickListener() {
            Expression = Expression + "8"
            binding.Result.text = Expression
        }
        binding.button9.setOnClickListener() {
            Expression = Expression + "9"
            binding.Result.text = Expression
        }




        //Operation buttons


            binding.divide.setOnClickListener() {
                //Go to line 288 for isExpressionValid function explanation
                if (isExpressionValid(Expression)) {
                    Expression += "/"
                    binding.Result.text = Expression
                }
            }


            binding.multiply.setOnClickListener() {
                if (isExpressionValid(Expression)) {
                    Expression += "*"
                binding.Result.text = Expression
                }
            }

            binding.deduction.setOnClickListener() {
                if (isExpressionValid(Expression)) {
                    Expression += "-"
                    binding.Result.text = Expression
                }
            }

            binding.add.setOnClickListener() {
                if (isExpressionValid(Expression)) {
                    Expression += "+"
                    binding.Result.text = Expression
                }
            }




        //Special Operation buttons

        //Resetting all the numbers and operation that has been written
        binding.Reset.setOnClickListener() {

            Expression = ""
            binding.Result.text = Expression

        }


        //Deleting the last character in Expression and at UI
        binding.delete.setOnClickListener() {

            Expression = Expression.dropLast(1)
            binding.Result.text = Expression

        }

       //Button that is standing for to make calculations with decimal numbers
        binding.dot.setOnClickListener() {

            //Preventing user to type '.' at first
            if (Expression.isNotEmpty()) {

                //Enabling user to type '.' in first number and preventing to type it twice at first number
                if (!(setOf('-', '*', '+', '/').any { Expression.contains(it) }) && !(Expression.contains('.'))
                ) {
                    Expression += "."
                    binding.Result.text = Expression
                }

                //Enabling user to type '.' in second number, preventing to write it just after a math operation and preventing to type it twice at second number
                if ((setOf('-', '*', '+', '/').any { Expression.contains(it) }) && !isLastCharSpecial(Expression)) {
                    val lastOperatorIndex = Expression.lastIndexOfAny(charArrayOf('-', '*', '+', '/'))
                    val substringAfterOperator = Expression.substring(lastOperatorIndex + 1)

                    if (!substringAfterOperator.contains('.')) {
                        Expression += "."
                        binding.Result.text = Expression
                    }
                }
            }
        }








                //Result(=) button algorithm
                binding.equals.setOnClickListener() {

                    if (Expression.isNotEmpty() && !setOf('/', '*', '-', '+').any { Expression.endsWith(it) }) {
                    var op = ""


                    when {
                        Expression.contains('/') -> {
                            op = "/"
                        }

                        Expression.contains('*') -> {
                            op = "*"
                        }

                        Expression.contains('-') -> {
                            op = "-"
                        }

                        Expression.contains('+') -> {
                            op = "+"
                        }


                    }


                    if (Expression[Expression.lastIndex] != '/' && Expression[Expression.lastIndex] != '*' && Expression[Expression.lastIndex] != '-' && Expression[Expression.lastIndex] != '+') {
                        if (op.isNotEmpty()) {
                            var operatorIndex = Expression.indexOf(op)
                            var firstString = Expression.substring(0, operatorIndex)
                            var secondString = Expression.substring(operatorIndex + 1)


                            when {

                                (op == "/" && secondString != "0") -> binding.Result.text = (Divide((firstString.toDouble()), (secondString.toDouble()))).toString()

                                //This one is here to prevent dividing to 0 exception
                                (op == "/" && secondString == "0") -> binding.Result.text = "Cannot divide with 0"

                                (op == "*") -> binding.Result.text = Multiply((firstString.toDouble()), (secondString.toDouble())).toString()

                                (op == "-") -> binding.Result.text = Deduct( (firstString.toDouble()), (secondString.toDouble()) ).toString()

                                (op == "+") -> binding.Result.text = Sum( (firstString.toDouble()), (secondString.toDouble())).toString()

                                (op == "") -> binding.Result.text = Expression

                            }

                            //Reseting the expression after the caluculation is done
                            Expression = ""


                        }

                    }

                }


            }


        }






    }



    //Calculation functions
    //Decimal Format methods are standing for returning last 8 digits after '.'

fun Divide(firstNumber: Double, secondNumber: Double): Double {
    val result = firstNumber / secondNumber
    return EightDecimal(result)
}


fun Multiply(firstNumber: Double, secondNumber: Double):Double{

    val result =  firstNumber * secondNumber
    return EightDecimal(result)
}

    fun Deduct(firstNumber: Double, secondNumber: Double):Double{

        val result = firstNumber - secondNumber
        return EightDecimal(result)
    }

    fun Sum(firstNumber: Double, secondNumber: Double):Double{

        val result = firstNumber + secondNumber
        return EightDecimal(result)
    }



    //Formatting Functions
    //Function to convert calculation result max 8 digit after '.' if needed
    fun EightDecimal(result: Double):Double{

        return DecimalFormat("#.########").format(result).toDouble()

    }




//Condition Functions

// isExpressionValid method is standing to control if any operation (+,-,*,/) written more than one time and
// to prevent writing them at start (example: $Expression = +24212) <- To prevent that

fun isExpressionValid(expression: String): Boolean {
    return expression.isNotEmpty() && !setOf('-', '*', '+', '/').any { expression.contains(it) }
}



fun isLastCharSpecial(expression: String): Boolean {
    val lastChar = expression.lastOrNull()
    val isIt = lastChar != null && lastChar in "-+*/"

    return isIt
}




