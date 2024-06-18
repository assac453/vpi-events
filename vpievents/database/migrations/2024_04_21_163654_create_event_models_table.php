<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration {
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('event_models', function (Blueprint $table) {
            $table->uuid('id')->primary();
            $table->uuid('event_type_id');
            $table->foreign('event_type_id')->references('id')->on('event_types')->onDelete('cascade');
            $table->timestamps();
            $table->string('name');
            $table->string('address');
            $table->double('longitude');
            $table->double('latitude');
            $table->text('description');
            $table->integer('points');
            $table->string('qrcode')->nullable();
            $table->string('image');
            $table->dateTime('begin');
            $table->dateTime('end');
        });

    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('event_models');
    }
};
