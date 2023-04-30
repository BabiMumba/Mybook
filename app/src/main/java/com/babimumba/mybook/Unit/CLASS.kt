package com.babimumba.mybook.Unit

import com.babimumba.mybook.Activity.*
import com.babimumba.mybook.Auth.AuthActivity
import com.babimumba.mybook.Auth.ForgetPasswordActivity
import com.babimumba.mybook.Auth.LoginActivity
import com.babimumba.mybook.Auth.RegisterActivity
import com.babimumba.schoolapp.Activity.*

object CLASS {
    var AUTH: Class<*> = AuthActivity::class.java
    var MAIN: Class<*> = MainActivity::class.java
    var SPLASH: Class<*> = SplashActivity::class.java
    var LOGIN: Class<*> = LoginActivity::class.java
    var REGISTER: Class<*> = RegisterActivity::class.java
    var CATEGORY_BOOKS: Class<*> = BooksCategoryActivity::class.java
    var BOOK_ADD: Class<*> = BookAddActivity::class.java
    var BOOK_EDIT: Class<*> = BookEditActivity::class.java
    var BOOK_DETAIL: Class<*> = BookDetailsActivity::class.java
    var BOOK_VIEW: Class<*> = BookViewActivity::class.java
    var PROFILE: Class<*> = ProfileActivity::class.java
    var PROFILE_EDIT: Class<*> = ProfileEditActivity::class.java
    var PROFILE_INFO: Class<*> = ProfileInfoActivity::class.java
    var EXPLORE_PUBLISHERS: Class<*> = ExplorePublishersActivity::class.java
    var FOLLOWERS: Class<*> = FollowersActivity::class.java
    var FOLLOWING: Class<*> = FollowingActivity::class.java
    var MY_BOOKS: Class<*> = MyBooksActivity::class.java
    var FAVORITES: Class<*> = FavoritesActivity::class.java
    var PRIVACY_POLICY: Class<*> = PrivacyPolicyActivity::class.java
    var FORGET_PASSWORD: Class<*> = ForgetPasswordActivity::class.java
    var MORE_BOOKS: Class<*> = MoreBooksActivity::class.java
}