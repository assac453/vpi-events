<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class PersonalInformation extends Model
{
    use HasFactory;
    protected $fillable = [
        'user_id', 'birth_date', 'last_name', 'first_name', 'middle_name', 'gender',
    ];

    public function user()
    {
        return $this->belongsTo(User::class);
    }
}
