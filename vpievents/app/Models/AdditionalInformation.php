<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class AdditionalInformation extends Model
{
    use HasFactory;
    protected $fillable = [
        'user_id', 'passport_series', 'passport_number', 'passport_issue_date', 'registration_address', 'residential_address', 'disability', 'orphan',
    ];

    public function user()
    {
        return $this->belongsTo(User::class);
    }
}
