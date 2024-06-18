<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;

class UserApiController extends Controller
{
    public function getAll()
    {
        $users = User::all();
        return response()->json($users);
    }

    public function AllUsers() 
    {
        $users = User::with(['personalInformation', 'additionalInformation', 'contactInformation', 'schoolInformation'])->where('id', '23ff1aea-80bd-4396-8caf-2a512e1fdade')->get();
        return response()->json(['users'=>$users]);
    }
}
