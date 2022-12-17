package menu.service

import camp.nextstep.edu.missionutils.Randoms
import menu.domain.Category
import menu.domain.Coach
import menu.utils.Constant.DAYS
import menu.view.InputView
import menu.view.OutputView

class RecommendService {
    fun run() {
        OutputView().printStartMessage()
        val coaches = InputView().getCoaches()
        val categories = generateCategories()
        recommendMenus(coaches, categories)
        OutputView().printRecommendMenuResult(coaches, categories)
    }

    private fun recommendMenus(coaches: List<Coach>, categories: List<String>) {
        categories.forEach { category ->
            coaches.forEach { coach ->
                val menu = generateRandomMenu(coach, category)
                coach.addMenu(menu)
            }
        }
    }

    private fun generateRandomMenu(coach: Coach, category: String): String {
        val menus = Category.getMenuList(category)
        var menu = Randoms.shuffle(menus)[0]
        while (coach.containHateMenus(menu) || coach.containMenu(menu)) {
            menu = Randoms.shuffle(menus)[0]
        }
        return menu
    }

    private fun generateCategories(): List<String> {
        val categories = mutableListOf<String>()
        repeat(DAYS) { categories.add(selectCategory(categories)) }
        return categories
    }

    private fun selectCategory(categories: List<String>): String {
        while (true) {
            val categoryName = generateRandomCategory()
            var count = 0
            categories.forEach { category ->
                if (category == categoryName) count++
            }
            if (count < 2) {
                return categoryName
            }
        }
    }

    private fun generateRandomCategory(): String {
        val categoryNumber = Randoms.pickNumberInRange(1, 5)
        return Category.getCategoryName(categoryNumber)
    }
}