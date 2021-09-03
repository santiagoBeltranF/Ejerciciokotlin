package co.edu.eam.disenosoftware.ejercicio

import co.edu.eam.disenosoftware.ejercicio.model.Account
import co.edu.eam.disenosoftware.ejercicio.model.Customer
import co.edu.eam.disenosoftware.ejercicio.model.TypeAccountEnum
import java.util.*

var customers = mutableListOf<Customer>()

fun createCustomer(name: String, lastName:String, city: String, savingQty: Int, checkQty:Int):Customer {
    val customer = Customer(name,lastName, Date().time.toString(), city)
    val accounts = mutableListOf<Account>()
    for (i in 1..savingQty) {
        accounts.add(Account(Date().time.toString(), 1000.0, TypeAccountEnum.SAVE))
    }

    for (i in 1..checkQty) {
        accounts.add(Account(Date().time.toString(), 2000.0, TypeAccountEnum.CHECK))
    }

    customer.accounts = accounts
    return customer
}

fun createAll() {
    customers = mutableListOf<Customer>(
        createCustomer("juan", "ferrer", "bogota",3,4),
        createCustomer("camilo", "ferrer", "armenia",2,0),
        createCustomer("claudia", "fernandez", "armenia",0,2),
        createCustomer("gladys", "bustos", "armenia",4,5),
        createCustomer("fabian", "baca", "armenia",1,2),
        createCustomer("diego", "santamaria", "el caimo",2,3),
        createCustomer("jose", "ariza", "montenegro",4,3),
        createCustomer("clara", "muñoz", "montenegro",1,3),
        createCustomer("hernando", "ariza", "tebaida",0,1),
        createCustomer("yojan", "franco", "bogota",1,0),
        createCustomer("jorge", "franco", "bogota",5,6),
        createCustomer("amalia", "franco", "bogota",7,8),
        createCustomer("julieth", "lopez", "calarca",1,1),
        )
}


fun main() {
    createAll()
    println(customers)
    println("all money ${getSumAllAccountsAllClients()}")
    println("all money in check accounts ${getSumCheckAccountsAllClients()}")
    println("all money in saving accounts ${getSumSavingAccountsAllClients()}")
    println("cities ${getCities()}")
    println("customers by city ${getCustomersByCity("armenia")}")
    println("customer claudia ${getCustomerByName("claudia")}")
    println("money by cities ${getBalanceByCities()}")
    println("all money in check accounts ${getSumCheckAccountsAllClients()}")
    println("get richest customer ${getCustomerWithMostMoney()}")
    println("get poorest customer ${getCustomerWithLeastMoney()}")
    println("customer sort by balance DESC ${sortCustomerByBalanceDesc()}")
}

/**
 * retorna la suma total del dinero del banco
 */
fun getSumAllAccountsAllClients():Double {
    return customers.sumOf { it.calculateBalance() }
}
/**
 * retorna la suma total del dinero del banco en cuentas de cheques
 */
fun getSumCheckAccountsAllClients():Double {
    return customers.sumOf { it.calculateBalanceCheckAccounts() }
}

/**
 * retorna la suma total del dinero del banco en cuentas de ahorros
 */
fun getSumSavingAccountsAllClients():Double {
    return customers.sumOf { it.calculateBalanceSavingAccounts() }
}

/**
 * retorna las diferentes ciudades donde estan los clientes del banco
 */
fun getCities():List<String> {
    val ciudadesClientes= customers.map { it.city }.distinct()
    return ciudadesClientes
}

fun getCustomersByCity(city: String): List<Customer>? {
    val customer= customers.filter { it.city== city}
    return customer
}

/**
 * retorna un mapa con la suma del saldo por ciudad de todos los clientes
 */
fun getBalanceByCities(): Map<String,Double> {
    return mapOf("armenia" to customers.filter { it.city == "armenia" }.sumOf { it.accounts.sumOf { it.balance } },"bogota" to customers.filter { it.city == "bogota" }.sumOf { it.accounts.sumOf { it.balance } },"El caimo" to customers.filter { it.city == "el caimo" }.sumOf { it.accounts.sumOf { it.balance }},"montenegro" to customers.filter { it.city == "montenegro" }.sumOf { it.accounts.sumOf { it.balance }},"montenegro" to customers.filter { it.city == "montenegro" }.sumOf { it.accounts.sumOf { it.balance }},"calarca" to customers.filter { it.city == "calarca" }.sumOf { it.accounts.sumOf { it.balance }})
}

/**
 * buscar cliente por nombre
 */
fun getCustomerByName(name: String): Customer? {
    val buscarCliente = customers.find { it.name==name }

    return buscarCliente
}

/**
 * retorna el cliente con mas dinero
 * Consultar: maxByOrNull
 */
fun getCustomerWithMostMoney(): Customer? {
    val mayorBalance=customers.map { it.calculateBalance() }.maxByOrNull { it }
    return customers.find { it.accounts.sumOf { it.balance }==mayorBalance}
}

/**
 * retorna el cliente con menos dinero
 * Consultar: minByOrNull
 */
fun getCustomerWithLeastMoney(): Customer? {
    val menorBalance= customers.map { it.calculateBalance() }.minByOrNull { it }
    return customers.find { it.accounts.sumOf { it.balance }==menorBalance}
}

/**
 * ordenar clientes por balance de menor a mayor
 */
fun sortCustomerByBalanceDesc(): List<Customer> {
    val ordenar= customers.map { it}
    return ordenar.sortedBy { it.accounts.sumOf { -it.balance } }
}