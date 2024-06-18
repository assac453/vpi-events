<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class StatusRequest extends Model
{
    use HasFactory;
    protected $table = 'request_statuses';
    protected $fillable = ['name'];
    protected $keyType = 'string'; // Добавьте это
    public $incrementing = false; // Добавьте это

    public function requests()
    {
        return $this->hasMany(Request::class);
    }
}
