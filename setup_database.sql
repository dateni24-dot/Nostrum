-- Script para crear la base de datos SQLite
-- Ejecutar esto antes de usar la aplicación Nostrum

-- Crear tabla Users si no existe (SQLite)
CREATE TABLE IF NOT EXISTS Users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  username TEXT UNIQUE NOT NULL,
  password TEXT NOT NULL
);

-- Crear índice para búsquedas rápidas
CREATE INDEX IF NOT EXISTS idx_username ON Users(username);

-- Verificar que la tabla se creó correctamente
-- En SQLite, usa: .schema Users
