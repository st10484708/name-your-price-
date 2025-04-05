package vcmsa.ci.nameyour_price


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.widget.*


class MainActivity : AppCompatActivity() {
    private lateinit var incomeInput: EditText
    private lateinit var expenseNameInputs: List<EditText>
    private lateinit var expenseAmountInputs: List<EditText>
    private lateinit var totalIncomeText: TextView
    private lateinit var totalExpensesText: TextView
    private lateinit var balanceText: TextView
    private lateinit var balanceFeedback: TextView
    private lateinit var expenseFeedback: TextView
    private lateinit var calculateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Link Views
        incomeInput = findViewById(R.id.incomeInput)

        expenseNameInputs = listOf(
            findViewById(R.id.expenseName1),
            findViewById(R.id.expenseName2),
            findViewById(R.id.expenseName3),
            findViewById(R.id.expenseName4)
        )

        expenseAmountInputs = listOf(
            findViewById(R.id.expenseAmount1),
            findViewById(R.id.expenseAmount2),
            findViewById(R.id.expenseAmount3),
            findViewById(R.id.expenseAmount4)
        )

        totalIncomeText = findViewById(R.id.totalIncome)
        totalExpensesText = findViewById(R.id.totalExpenses)
        balanceText = findViewById(R.id.balance)
        balanceFeedback = findViewById(R.id.balanceFeedback)
        expenseFeedback = findViewById(R.id.expenseFeedback)
        calculateButton = findViewById(R.id.calculateButton)

        calculateButton.setOnClickListener {
            calculateBalance()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateBalance() {
        val incomeStr = incomeInput.text.toString()

        if (incomeStr.isBlank()) {
            Toast.makeText(this, "Please enter your income.", Toast.LENGTH_SHORT).show()
            return
        }

        val income = incomeStr.toDoubleOrNull()
        if (income == null || income <= 0) {
            Toast.makeText(this, "Enter a valid income amount.", Toast.LENGTH_SHORT).show()
            return
        }

        var totalExpenses = 0.0

        for (i in expenseAmountInputs.indices) {
            val name = expenseNameInputs[i].text.toString()
            val amountStr = expenseAmountInputs[i].text.toString()

            if (name.isBlank()) {
                Toast.makeText(this, "Enter category for expense ${i + 1}.", Toast.LENGTH_SHORT).show()
                return
            }

            val amount = amountStr.toDoubleOrNull()
            if (amount == null || amount < 0) {
                Toast.makeText(this, "Enter a valid amount for \"$name\".", Toast.LENGTH_SHORT).show()
                return
            }

            totalExpenses += amount
        }

        val balance = income - totalExpenses

        totalIncomeText.text = "Total Income: $%.2f".format(income)
        totalExpensesText.text = "Total Expenses: $%.2f".format(totalExpenses)
        balanceText.text = "Balance: $%.2f".format(balance)

        if (balance >= 0) {
            balanceFeedback.text = "Keep that bag up."
            balanceFeedback.setTextColor(Color.parseColor("#388E3C")) // green
        } else {
            balanceFeedback.text = "Lets try saving"
            balanceFeedback.setTextColor(Color.RED)

        }
    }
}