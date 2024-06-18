<?php

namespace Database\Seeders;

use App\Models\StatusRequest;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Str;

class RequestStatusSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        StatusRequest::create(['name' => 'Одобрено']);
        StatusRequest::create(['name' => 'Отклонено']);
        StatusRequest::create(['name' => 'Рассматривается']);
    }
}
