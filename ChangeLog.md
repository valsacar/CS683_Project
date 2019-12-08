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

2 Dec 19:

Created MathBattle to handle making the math problems and answer options.
Modified MathCharacter to be able to generate enemies.
Moved some parts of MathItem/MathArmor/MathWeapon to MathBattle.
Added xpToNext() to MathCharacter and displayed in ViewCharActivity.
Added OnClickSubtract to MathFightActivity/activity_math_fight.xml.

3 Dec 19:
Added addLevel logic to MathCharacter.
Added all logic for handling fights, xp gain, gold gain, etc.  Logic for determining the problems still needs to be done.

6-9 Dec 19:
Lots of work over the weekend.
Updated MathFightActivity to handle the fights.
Created HelpActivity and HelpTopicActivity to display help files.
With play tester report adjusted the cost of potions and increased the starting number of potions.
Various bug fixes and balance changes to nearly all aspects of items, characters and fights.
App is feature complete per the original designed essential functions and a few of the desirable ones.