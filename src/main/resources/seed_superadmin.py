import psycopg2
import uuid
from datetime import datetime
import bcrypt

# Database configuration from application.properties
DB_CONFIG = {
    "dbname": "test",
    "user": "postgres",
    "password": "gaurav123",  # Updated as per your application.properties
    "host": "localhost",
    "port": "5432"
}

def hash_password(password):
    # Spring Security BCryptPasswordEncoder uses a strength of 10 by default
    salt = bcrypt.gensalt(rounds=10)
    return bcrypt.hashpw(password.encode('utf-8'), salt).decode('utf-8')

def seed_data():
    conn = None
    try:
        # Connect to PostgreSQL
        conn = psycopg2.connect(**DB_CONFIG)
        cur = conn.cursor()

        # 1. Create Organisation
        org_id = str(uuid.uuid4())
        org_name = "Gryphon Academy"
        now = datetime.now()

        print(f"Creating organisation: {org_name}...")
        cur.execute(
            """
            INSERT INTO organisations (id, name, created_at, updated_at)
            VALUES (%s, %s, %s, %s)
            ON CONFLICT (id) DO NOTHING;
            """,
            (org_id, org_name, now, now)
        )

        # 2. Create Super Admin User
        user_id = str(uuid.uuid4())
        user_email = "superadmin@gryphonacademy.co.in"
        user_password = "password123"
        password_hash = hash_password(user_password)

        print(f"Creating superadmin user: {user_email}...")
        cur.execute(
            """
            INSERT INTO users (
                id, name, email, role, password_hash, 
                password_provider, organisation_id, created_at, updated_at
            )
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)
            ON CONFLICT (email) DO UPDATE 
            SET password_hash = EXCLUDED.password_hash, role = EXCLUDED.role;
            """,
            (
                user_id,
                "Super Admin",
                user_email,
                "SUPERADMIN", # <--- Fixed: Changed from SUPER_ADMIN to SUPERADMIN
                password_hash,
                "LOCAL",
                org_id,
                now,
                now
            )
        )

        conn.commit()
        print("Successfully seeded Organisation and Super Admin!")

    except Exception as e:
        print(f"Error: {e}")
        if conn:
            conn.rollback()
    finally:
        if conn:
            cur.close()
            conn.close()

if __name__ == "__main__":
    seed_data()