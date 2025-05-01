# Finance-Tracker-App-FinTech
 Finance Tracker app by kotlin
What is SharedPreferences?
SharedPreferences lets you save key-value pairs of primitive data types (like String, Int, Boolean, etc.). It's persistent — even if you close the app, the data remains.

It’s perfect for lightweight local storage — like remembering if the user is logged in.

🔑 Example Use Cases:
Save login state (isLoggedIn = true)

Store user email or username

Track if onboarding is completed

Save app theme preference (dark/light)

✍️ How to Use SharedPreferences in Kotlin:
1. Save Data:
kotlin
Copy
Edit
val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
val editor = sharedPref.edit()
editor.putString("username", "john_doe")
editor.putBoolean("isLoggedIn", true)
editor.apply()
apply() saves the data asynchronously. You can also use commit() for synchronous saving.

2. Retrieve Data:
kotlin
Copy
Edit
val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
val username = sharedPref.getString("username", "defaultUser")
val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
3. Clear or Remove Specific Value:
kotlin
Copy
Edit
val editor = sharedPref.edit()
editor.remove("username") // remove one key
// or
editor.clear() // clear all saved data
editor.apply()
🔐 Where Is the Data Stored?
Android saves SharedPreferences as an XML file in your app's private internal storage:
/data/data/<your.package.name>/shared_prefs/MyPrefs.xml

🧠 Pro Tip:
If you're using SharedPreferences a lot, consider wrapping it in a helper class or singleton to manage keys and access more cleanly.



Stay on top of your money with Finance Tracker. Easily record expenses, monitor budgets, and visualize where your money goes—perfect for smarter financial decisions."



