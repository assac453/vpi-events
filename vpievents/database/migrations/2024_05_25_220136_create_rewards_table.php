<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
use Illuminate\Support\Str;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('rewards', function (Blueprint $table) {
            $table->uuid('id')->primary();
            $table->string('name');
            $table->integer('points_required');
            $table->string('image')->nullable();
            $table->integer('quantity');
            $table->uuid('status_id'); // Добавляем поле для связи с таблицей статусов
            $table->timestamps();

            $table->foreign('status_id')->references('id')->on('statuses')->onDelete('cascade');
        });
    }

    protected static function boot()
    {
        parent::boot();

        static::creating(function ($model) {
            $model->id = (string) Str::uuid();
        });
    }
    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('rewards');
    }
};
