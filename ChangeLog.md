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