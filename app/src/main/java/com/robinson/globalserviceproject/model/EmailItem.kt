package com.robinson.globalserviceproject.model

data class EmailItem(var id: String, var email: String = "", var type: String)

/*class EmailItem(id: String, s: String, selectedDepartment: String) {
    var id: String = "0"
    var email: String = ""
    var type: String = ""

}*/

/*data class Book(var name: String, var authorName: String = "Anupam", var lastModified: Long = 1234567, var rating: Float = 5f, var downloads: Int = 1000)
fun main(args: Array<String>) {
    var book = Book("Android tutorials","Anupam", 1234567, 4.5f, 1000)

    book = Book("Kotlin")
    book = Book("Swift",downloads = 500)
    book = Book("Java","Pankaj",rating = 5f, downloads = 1000)
    book = Book("Python","Shubham",rating = 5f)

}*/
