package com.example.shoppinglisttesting

object RegistrationUtil {
    private val existingUserName = listOf("peter","carl")
    /**
     * the input is  not valid  is empty
     * ... the username and password must not empty
     * ... the username is already taken
     * ... the comfirmedPassword is not the same as the real password
     * ... the password contains less than 2 digits
     */

    fun validateRegistrationInput(
        username:String,
        password :String,
        confirmedPassword :String
    ): Boolean{
           if ( username.isEmpty() || password.isEmpty()){
               return false
           }
        if (username in existingUserName){
            return false
        }
        if (password != confirmedPassword){
            return false
        }
        if (password.count { it.isDigit()}<2) {
            return false
        }
        if (password != confirmedPassword){
            return false
        }
        return true
    }
}