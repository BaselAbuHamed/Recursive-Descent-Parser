# Modula-2 Subset Recursive Descent Parser

This project implements a recursive descent parser for a subset of the MODULA-2 programming language. The parser is designed to recognize and parse valid MODULA-2 code based on a specified subset of grammar rules.

## Grammar Rules

The parser adheres to the following grammar rules:


## Module Declaration

- `module-decl` → `module-heading declarations procedure-decl block name .`
- `module-heading` → `module name ;`
- `block` → `begin stmt-list end`
- `declarations` → `const-decl var-decl`
- `const-decl` → `const const-list | ε`  
- `const-list` → `( name = value ; )*`
- `var-decl` → `var var-list | ε`
- `var-list` → `( var-item ; )*`
- `var-item` → `name-list : data-type`
- `name-list` → `name ( , name )*`
- `data-type` → `integer | real | char`

  ## Procedure Declaration

- `procedure-decl` → `procedure-heading declarations block name ;`
- `procedure-heading` → `procedure name ;`
- `stmt-list` → `statement ( ; statement )*`
- `statement → ass-stmt|read-stmt | write-stmt | if-stmt | while-stmt | repeat-stmt | exit-stmt | call-stmt | ε`

  ## Assignment Statement

- `ss-stmt` → `name := exp`

  ## Expression

- `exp` → `term ( add-oper term )*`
- `term` → `factor ( mul-oper factor )*`
- `factor` → `( exp ) | name | value`
- `add-oper` → `+ | -`
- `mul-oper` → `* | / | mod | div`

  ## Read Statement

- `read-stmt` → `readint ( name-list ) | readreal ( name-list ) | readchar ( name-list ) | readln`

## Write Statement

- `write-stmt` → `writeint ( write-list ) | writereal ( write-list ) | writechar ( write-list ) | writeln`
- `write-list` → `write-item ( , write-item )*`
- `write-item` → `name | value`

## If Statement

- `if-stmt` → `if condition then stmt-list elseif-part else-part end`
- `elseif-part` → `( elseif condition then stmt-list )*`
- `else-part` → `else stmt-list | ε`

## While Statement

- `while-stmt` → `while condition do stmt-list end`

## Repeat Statement

- `repeat-stmt` → `loop stmt-list until condition`

## Exit Statement

- `exit-stmt` → `exit`

## Call Statement

- `call-stmt` → `call name (* This is a procedure name *)`

## Condition

- `condition` → `name-value relational-oper name-value`
- `name-value` → `name | value`
- `relational-oper` → `= | |= | < | <= | > | >=`

## Identifier

- `name` → `letter ( letter | digit )*`

## Values

- `value` → `integer-value | real-value`
- `integer-value` → `digit ( digit )*`
- `real-value` → `digit ( digit )* . digit ( digit )*`

  ## How to Use

### Prerequisites

- **Java:** Ensure you have Java installed on your system.

### Clone and Compile

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/BaselAbuHamed/Recursive-Descent-Parser.git
   cd Recursive-Descent-Parser
   ```

2. Compile and run the application:

    ```bash
    cd Recursive-Descent-Parser
    javac LexicalAnalyzerTest.java
    java LexicalAnalyzerTest
    ```
