# TodoApp

## 概要
タスクを追加、削除、編集、完了といった操作を行えるシンプルなToDoアプリ

## デモ


## 機能一覧
1. **ユーザー登録・ログイン**
   - アカウント作成機能
   - ログイン機能

2. **タスクの作成・編集・削除**
   - 新しいタスクの作成
   - 既存タスクの編集
   - タスクの削除

3. **タスクの一覧表示**
   - ユーザーのタスクを一覧表示

4. **タスクの完了/未完了の管理**
   - タスクの完了/未完了のマーク

5. **タスクのカテゴリー・優先度分け**
   - タスクをカテゴリー・優先度別に分類
   - カテゴリー・優先度ごとにフィルタリングしてタスクを表示

6. **期限設定**
   - タスクに締切日を設定

7. **検索機能**
   - タスク名で検索

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

