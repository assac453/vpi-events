<?php

namespace Database\Seeders;

use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        User::create([
            'id' => Str::uuid(),
            'email' => 'admin@mail.ru',
            'password' => Hash::make('admin123'),
            'role_id' => 1, // assuming 1 is the ID for admin
            'points' => 100,
        ]);

        User::create([
            'id' => '78efaa4e-46d5-367f-9867-904c55b471a7',
            'email' => 'ivanivanovich@mail.ru',
            'password' => Hash::make('password'),
            'role_id' => 2, // assuming 2 is the ID for student
            'points' => 50,
        ]);

        User::create([
            'id' => Str::uuid(),
            'email' => 'tarasov.alexander.artemovich@example.com',
            'password' => Hash::make('password'),
            'role_id' => 2, // assuming 2 is the ID for student
            'points' => 0,
        ]);

        User::create([
            'id' => Str::uuid(),
            'email' => 'matveeva.polina.kirillovna@example.com',
            'password' => Hash::make('password'),
            'role_id' => 2, // assuming 2 is the ID for student
            'points' => 0,
        ]);
        User::create([
            'id' => Str::uuid(),
            'email' => 'gracheva.arina.matveevna@example.com',
            'password' => Hash::make('password'),
            'role_id' => 2, // assuming 2 is the ID for student
            'points' => 0,
        ]);
        User::create([
            'id' => Str::uuid(),
            'email' => 'simonov.david.semenovich@example.com',
            'password' => Hash::make('password'),
            'role_id' => 2, // assuming 2 is the ID for student
            'points' => 0,
        ]);

        User::create([
            'id' => Str::uuid(),
            'email' => 'tokarev.timofey.igorevich@example.com',
            'password' => Hash::make('password'),
            'role_id' => 2, // assuming 2 is the ID for student
            'points' => 0,
        ]);

        User::create([
            'id' => Str::uuid(),
            'email' => 'vinogradov.daniil.igorevich@example.com',
            'password' => Hash::make('password'),
            'role_id' => 2, // assuming 2 is the ID for student
            'points' => 0,
        ]);

        User::create([
            'id' => Str::uuid(),
            'email' => 'fokina.varvara.maximovna@example.com',
            'password' => Hash::make('password'),
            'role_id' => 2, // assuming 2 is the ID for student
            'points' => 0,
        ]);
        User::create([
            'id' => Str::uuid(),
            'email' => 'kovaleva.ailin.glebovna@example.com',
            'password' => Hash::make('password'),
            'role_id' => 2, // assuming 2 is the ID for student
            'points' => 0,
        ]);

        User::create([
            'id' => Str::uuid(),
            'email' => 'morozova.zoya.alexandrovna@example.com',
            'password' => Hash::make('password'),
            'role_id' => 2, // assuming 2 is the ID for student
            'points' => 0,
        ]);

        User::create([
            'id' => Str::uuid(),
            'email' => 'ivanov.dmitry.sergeevich@example.com',
            'password' => Hash::make('password'),
            'role_id' => 2, // assuming 2 is the ID for student
            'points' => 0,
        ]);




    }
}
