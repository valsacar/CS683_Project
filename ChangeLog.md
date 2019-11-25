16 Nov 19:

Updated MathCharacter - new method to calculate max hp based off provided health stat
Updated view char - Save button no longer closes, Close button closes
Updated view char - Use calculateMaxHP() from MathCharacter to update HP when changing Health stat.

17 Nov 19:

Created MathItem abstract class and it's two varients MathWeapon and MathArmor.
Created ViewShopActivity and related BuyWeaponFragment for handling item purchase. (Probably will rename BuyWeaponFragment as it is modular enough to handle both weapon and armor).
Added action bar to ViewCharActivity and ViewShopActivity.
Added another crappy stick drawing for the shop.
Updated MainActivity to launch ViewShopActivity.
Modified MathCharacter to use the new MathWeapon and MathArmor instead of ints. 

18 Nov 19:

Refactored BuyWeaponFragment into BuyMathItemFragment.
Completed Weapon upgrade purchases.
Implemented the buying of armor.
Implemented buying of potions.
Completed ViewShopActivity to save and pass back the results of purchases.

20 Nov 19:

Started to implement save game as saved preferences.

21 Nov 19:

Created PreferenceHelper to handle saving and loading, as well as other universal aspects.
Updated ViewCharActivity to use PreferenceHelper.

22 Nov 19:

Updated ViewShopActivity to use PreferenceHelper.
Fixed multiple bugs in ViewShopActivity and BuyMathItemFragment related to visual display and a few that allowed for free upgrades.
Created copy constructors for MathItem and children.
Updated BuyMathItemFragment to use a copy of the MathItem to simplify the code (fixing mentioned bugs), Updated ViewShopAcvity to match.

23 Nov 19:

Modified ViewCharActivity to use PreferenceHelper without using the 3 mod variables.
Updated MainActivity to fix the button overlap on smaller screens.
Created MathFightActivity and started the basic layout.