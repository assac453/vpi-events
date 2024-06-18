<?php

namespace Database\Seeders;

use App\Models\Status;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Str;

class StatusesSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        Status::create([
            'id' => Str::uuid(),
            'name' => 'В наличии',
        ]);
        Status::create([
            'id' => Str::uuid(),
            'name' => 'Закончились',
        ]);
    }
}
