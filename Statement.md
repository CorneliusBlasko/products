This challenge is aimed to test some basic knowledge when developing technical
solutions to a business need.
## Challenge
The business has requested the development of a scalable solution for managing the
company’s product catalog. The goal is to break down the existing monolithic application
into a microservices architecture. Our team is currently responsible for the Products API,
which will be a critical component of this new system. The requirements are described
below.
### Mandatory requirements
- Create a REST API application dedicated to serve the products catalog. A small copy of
the products to be served is provided below.
- Develop an endpoint to serve the products list.
- When serving the products list, take into account that we need to display a discount for
certain categories and special products:
- Electronics: 15%.
- Home & Kitchen: 25%
- SKUs ending in `5` like `SKU0005` will have an special 30% of discount.
- Only one discount can be applied at the same time; giving priority to the bigger
- The products can be filtered by `category`.
- The products can be sorted by `SKU`, `Price`, `Description` and `Category`
- The application should contain a `test` phase where basic features are tested.
- Brief explanation of architectural decisions.
- Use any good practice you think works the best here; since this will be treated as a
production feature.
### Nice to have
- API Documentation.
- Pagination.
- Integration tests.
## Products Data:
| SKU     | Precio | Producto                                        | Categoría         |
|---------|--------|-------------------------------------------------|-------------------|
| SKU0001 | 19.99  | Wireless Mouse with ergonomic design            | Electronics       |
| SKU0002 | 499.00 | 4K Ultra HD Smart TV, 55 inches                 | Electronics       |
| SKU0003 | 29.50  | Stainless Steel Water Bottle, 1L                | Home & Kitchen    |
| SKU0004 | 15.00  | Cotton T-Shirt, Unisex, Size M                  | Clothing          |
| SKU0005 | 120.00 | Noise-Cancelling Over-Ear Headphones            | Electronics       |
| SKU0006 | 9.99   | USB-C to USB Adapter                            | Electronics       |
| SKU0007 | 75.00  | Leather Wallet with RFID Protection             | Accessories       |
| SKU0008 | 35.00  | Yoga Mat with Non-Slip Surface                  | Sports            |
| SKU0009 | 220.00 | Smartwatch with Heart Rate Monitor              | Electronics       |
| SKU0010 | 12.50  | Ceramic Coffee Mug, 350ml                       | Home & Kitchen    |
| SKU0011 | 60.00  | Bluetooth Portable Speaker                      | Electronics       |
| SKU0012 | 85.00  | Backpack with Laptop Compartment                | Accessories       |
| SKU0013 | 18.00  | Stainless Steel Cutlery Set, 24 Pieces          | Home & Kitchen    |
| SKU0014 | 250.00 | Electric Guitar Starter Pack                    | Musical Instr.    |
| SKU0015 | 42.00  | Running Shoes, Men's Size 42                    | Footwear          |
| SKU0016 | 27.99  | Digital Bathroom Scale with Body Fat Analyzer   | Home Appliances   |
| SKU0017 | 14.99  | Set of 6 Organic Cotton Socks                   | Clothing          |
| SKU0018 | 300.00 | DSLR Camera with 18-55mm Lens                   | Electronics       |
| SKU0019 | 8.99   | Hardcover Notebook, A5, 200 Pages               | Stationery        |
| SKU0020 | 65.00  | Microwave Oven, 20L Capacity                    | Home Appliances   |
| SKU0021 | 23.50  | LED Desk Lamp with Adjustable Brightness        | Home & Kitchen    |
| SKU0022 | 19.00  | Wireless Charger Pad for Smartphones            | Electronics       |
| SKU0023 | 55.00  | Men's Quartz Analog Watch with Leather Strap    | Accessories       |
| SKU0024 | 30.00  | Wooden Chess Set with Folding Board             | Toys & Games      |
| SKU0025 | 99.00  | Home Security Camera with Night Vision          | Electronics       |
| SKU0026 | 16.50  | Aromatherapy Essential Oil Diffuser             | Home & Kitchen    |
| SKU0027 | 40.00  | Professional Blender with 2L Jar                | Home Appliances   |
| SKU0028 | 22.00  | Kids' Educational Tablet Toy                    | Toys & Games      |
| SKU0029 | 110.00 | Mechanical Gaming Keyboard with RGB Lighting    | Electronics       |
| SKU0030 | 7.50   | Pack of 10 Ballpoint Pens, Blue Ink             | Stationery        |

## Rules
- Supported languages: Java, Kotlin, Golang.
- Basic application startup guide must be provided.
- All the `mandatory requirements` should be delivered. The `nice to have` are optional.
- Upload it to a public repository and send us the link