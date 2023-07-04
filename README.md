# Final Project

My target was to build an application from scratch which will contain backend implementation. The idea is to build our Bank, which should have the following features:

### The user should:
-  Have the possibility to login / logout – Use Spring Security and the password should not be saved in plaintext
-  Be able to have multiple accounts in different currencies
-  Be able to see the available balance for all the accounts
-  Be able to exchange money from a currency to another (hardcoded exchange rate)
-  Be able to make a transaction to another user
-  Be able to request money from another user
-  Be able to see the transactions history (income/ outcome)

### Transaction:
-  An operation of money transfer between two accounts
-  After the transaction is executed, the balance is updated accordingly for both accounts

### Bonus
Use an HTTP Client (Rest Template) to call an external api to fetch the exchange rate.

You have to think of a way to design the database and apis. Feel free to create/add any validation you like in backend. You should prepare also a postman collection which will contain all your created endpoints.
https://www.youtube.com/watch?v=A-B3iyRjGY8&ab_channel=RobertPodgorencu
