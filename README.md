# Polytech-Math-Server

Polytech-Math-Server — сервер, выполняющий математические вычисления.

Проект написан на Kotlin.

## Оглавление

1. [Запрос](#Запрос)

2. [API Методы](#API-Методы)

3. [Рекомендации](#Рекомендации)


### Запрос

URL Сервера: polytech.annexflow.ru

### API Методы

API Polytech-Math-Server — это интерфейс, который позволяет получить результаты вычисления с помощью https-запросов к
специальному серверу. Синтаксис запросов и тип возвращаемых ими данных строго определены на
стороне самого сервиса.

### + __library.upload__ [POST]

Метод позволяющий загрузить библиотеку на сервер.

#### Выходные значения

id : Int - идентификатор библиотеки.

#### Mutlipart

Пользовательская библиотека в формате jar.

### + __library.remove__ [GET]

Метод позволяющий удалить библиотеку с сервера.

#### Параметры

id : Int - идентификатор библиотеки.

### + __library.calculate__ [WEBSOCKET]

Метод выполяющий вычисления с помощью указанной библиотеки.

#### Параметры

##### Объект запроса

library_id : Int - идентификатор библиотеки.

classes : List<ClassModel> - список с объектами класса ClassModel.

is_delayed : Boolean - включена ли функция искусственной задержки между callback'ами.

##### Объект ClassModel

name : String - Полное имя класса.

constructor_parameters : List<String> - список с параметрами конструктора.

methods : List<MethodModel> - идентификатор библиотеки.

##### Объект MethodModel

name : String - название метода.

parameters : List<String> - список с параметрами метода.
  
#### Выходные значения

Сервер выбрасывает значения типа String.
  
### Рекомендации

Рекомендую использовать Kotlin Serialization для передачи значений. (Пример присутствует в отдельном репозитории)
