# TodoApp

## 概要
タスクを追加、削除、編集、完了といった操作を行えるシンプルなToDoアプリ<br><br><br><br>

## デモ
### ログイン画面(login.html)
![ログイン画面](src/main/resources/static/img/sign-in.png)
初回は、「Sign up」からアカウントを作成する。<br><br><br>

### アカウント登録(register.html)
![アカウント作成画面](src/main/resources/static/img/sign-up.png)
必要項目を入力し、アカウントを作成する。<br><br><br>

### タスク一覧(taskList.html)
![タスク一覧画面](src/main/resources/static/img/taskList.png)
ログイン成功後、タスク一覧画面が表示される。<br><br><br>

### タスク追加(taskForm.html)
![タスク追加画面](src/main/resources/static/img/newtask.png)
タスク一覧の「Add New Task」を押下し、タスク追加画面に遷移する。<br><br><br>

### タスク編集
![タスク編集画面](src/main/resources/static/img/updatetask.png)
タスク一覧画面の既存のタスク名を押下し、タスク編集画面に遷移する。<br><br><br>

### タスク検索
![タスク検索結果画面](src/main/resources/static/img/listSearch.png)
タスク一覧画面上部の検索バーにタスク名の文字列を入力し、検索する。<br><br><br>

### 優先度別表示
![優先度別表示画面](src/main/resources/static/img/listPriority.png)
タスク一覧画面のPriorityのカラムから指定した優先度のタスクのみを表示する。<br><br><br>

### カテゴリ別表示
![カテゴリ別表示画面](src/main/resources/static/img/listCategory.png)
タスク一覧画面のCategoryのカラムから指定したカテゴリのタスクのみ表示する。<br><br><br>

## 機能一覧
1. **ユーザー登録・ログイン**
   - アカウント作成機能
   - ログイン機能
<br><br>
2. **タスクの作成・編集・削除**
   - 新しいタスクの作成
   - 既存タスクの編集
   - タスクの削除
<br><br>
3. **タスクの一覧表示**
   - ユーザーのタスクを一覧表示
<br><br>
4. **タスクの完了/未完了の管理**
   - タスクの完了/未完了のマーク
<br><br>
5. **タスクのカテゴリ・優先度分け**
   - タスクをカテゴリ・優先度別に分類
   - カテゴリ・優先度ごとにフィルタリングしてタスクを表示
<br><br>
6. **期限設定**
   - タスクに締切日を設定
<br><br>
7. **検索機能**
   - タスク名で検索<br><br><br><br>

## MySQLセットアップ
以下のクエリをMySQLで実行してください。
 ```sql
    CREATE DATABASE todoapp;

    USE todoapp;

    CREATE TABLE User (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(50) NOT NULL,
        password VARCHAR(100) NOT NULL,
        email VARCHAR(100) NOT NULL,
        role VARCHAR(255) DEFAULT 'USER'
    );

    CREATE TABLE Task (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        description TEXT,
        category VARCHAR(50),
        priority VARCHAR(20),
        dueDate DATE,
        completed BOOLEAN,
        user_id BIGINT,
        FOREIGN KEY (user_id) REFERENCES User(id)
    );

    CREATE TABLE category (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        category VARCHAR(255) NOT NULL
    );
    
    INSERT INTO category (category) VALUES ('仕事'), ('学校'), ('プライベート');

    CREATE TABLE priority (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        priority VARCHAR(255) NOT NULL
    );
    
    INSERT INTO priority (priority) VALUES ('高'), ('中'), ('低');


    INSERT INTO Task (name, description, category, priority, dueDate, completed, user_id) VALUES 
    ('Sample Task 1', 'This is a sample task description.', 'Work', 'High', '2024-12-31', false, 1),
    ('Sample Task 2', 'This is another sample task description.', 'Personal', 'Medium', '2024-11-30', true, 1),
    ('Javaの学習', 'Java Silverの本を進める', 'Work', 'Medium', '2024-05-31', false, 1);
```
<br><br><br><br>

## 使用技術
- **フロントエンド**
  - HTML
  - CSS
  - JavaScript（必要に応じて）

- **バックエンド**
  - Java
  - Spring Boot

- **データベース**
  - MySQL

- **テンプレートエンジン**
  - Thymeleaf

