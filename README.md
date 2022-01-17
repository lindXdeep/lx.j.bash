# Упрощенный эмулятор bash

Написать упрощенный эмулятор bash (эмулятор командной строки), который понимает несколько команд с возможностью их комбинирования

## Требования к программе

##### 1. Программа запускается и готова принимать команды пользователя

##### 2. В качестве prompt'а (приглашения ввода) программа отображает текущий каталог. Начальным каталогом может является, например, домашняя папка текущего пользователя

##### 3. Программа должна поддерживать следующие команды

- `help` - вывод всех поддерживаемых команд с небольшим описанием.
- `cd <directory name>` - переход в указанную директорию.
- `ls` - отображение списка файлов и директорий в текущей папке. 
  - Опционально - с атрибутами файлов и/или их владельцев.
- `mkdir <directory name>` - создание директории с указанным именем.
- `touch <file name>` - создание файла с указанным именем или обновление last modified данного файла если он уже создан.
- `cat <file name>` - чтение файла и вывод содержания.
- `tail <file name>` - вывод последних (по умолчанию 10) строк файла.
- `cp <file/directory name>` - копирование файла или директории. 
  - Опционально - с возможностью указания флага рекурсивного копирования.
- `exit` - завершение работы эмулятора.

##### 4. Программа устойчива ко вводу неизвестных команд (выводит человеко-читаемую ошибку)

##### 5. Программа должна поддерживать ввод нескольких команд через операнды:

- `&&` - (последовательное выполнение команд при успешном выполнении предыдущей)
- `||` - (последовательное выполнение команд все зависимости от успешного выполнения предыдущей) .
- `> ` - (перенаправление вывода в файл).

##### 6. Программа должна быть легко расширяема на возможность добавления новых команд или операндов команд

## Дополнительно

1. unit-тесты на реализованные компоненты
2. Интеграционные тесты на интерпретатор в целом

> ## *Будет оцениваться:*
> 1. *Качество и полнота сделанного задания*
> 2. *Понятность кода и архитектуры решения*
> 3. *Наличие и полнота тестов*
