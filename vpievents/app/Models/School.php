<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class School extends Model
{
    use HasFactory;
    protected $table = 'school';
    protected $fillable = [
        'name', 'address', 'longitude', 'latitude',
    ];

    public function schoolInformation()
    {
        return $this->hasOne(SchoolInformation::class);
    }
}
