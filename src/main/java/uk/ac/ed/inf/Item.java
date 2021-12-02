package uk.ac.ed.inf;

/**
 * Item class contains the item name and its cost in pence.
 * an ArrayList of Item is contained in the MenuDetails class
 * A HashMap of type (String, Item) is contained in the Menus Class
 */
public class Item {
        private String item;
        private int pence;
        private String store;
        private LongLat longLat;

        public String getItem() {
                return item;
        }

        public int getPence() {
                return pence;
        }

        public String getStore() {
                return store;
        }

        public LongLat getLongLat() {
                return longLat;
        }

        public void setItem(String item) {
                this.item = item;
        }

        public void setPence(int pence) {
                this.pence = pence;
        }

        public void setStore(String store) {
                this.store = store;
        }

        public void setLongLat(LongLat longLat) {
                this.longLat = longLat;
        }
}


