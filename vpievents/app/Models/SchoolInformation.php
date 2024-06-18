<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class SchoolInformation extends Model
{
    use HasFactory;
    protected $fillable = [
        'user_id', 'school_link', 'class',
    ];

    protected $keyType = 'string'; // Добавьте это
    public $incrementing = false; // Добавьте это

    public function user()
    {
        return $this->belongsTo(User::class);
    }
    public function school(){
        return $this->belongsTo(School::class);
    }
}
