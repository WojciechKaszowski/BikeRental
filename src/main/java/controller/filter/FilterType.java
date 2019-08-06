package controller.filter;

public enum FilterType implements FilterTypeInterface{

    BRAND {
        @Override
        public boolean executeFilter(String value, FilterController filterController) {
            return filterController.filterByBrand(value);
        }
    },
    COLOR {
        @Override
        public boolean executeFilter(String value, FilterController filterController) {
            return filterController.filterByColor(value);
        }
    },
    RENTAL_STATUS {
        @Override
        public boolean executeFilter(String value, FilterController filterController) {
            return filterController.filterByStatus(value);
        }
    },
    PRICE {
        @Override
        public boolean executeFilter(String value, FilterController filterController) {
            return filterController.filterByPrice(value);
        }
    }

}
