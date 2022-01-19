
const inkUsageSampleData = [
  {
    "Date": "2021/Dec/01",
    "Cyan": 1741.3242200000002,
    "Magenta": 1401.6901,
    "Yellow": 1539.1607500000002,
    "Black": 1485.4368099999997
  },
  {
    "Date": "2021/Dec/02",
    "Cyan": 1534.8651399999997,
    "Magenta": 1587.9712500000003,
    "Yellow": 1649.8671099999997,
    "Black": 1655.7642500000004
  },
  {
    "Date": "2021/Dec/03",
    "Cyan": 1557.5681299999997,
    "Magenta": 1615.7700799999993,
    "Yellow": 1771.26699,
    "Black": 1471.7606899999998
  },
  {
    "Date": "2021/Dec/04",
    "Cyan": 1868.1532399999999,
    "Magenta": 1820.2647600000003,
    "Yellow": 1854.47537,
    "Black": 2138.89282
  },
  {
    "Date": "2021/Dec/05",
    "Cyan": 1483.4407799999997,
    "Magenta": 1566.7443200000002,
    "Yellow": 1390.9770899999999,
    "Black": 1448.4219500000002
  },
  {
    "Date": "2021/Dec/06",
    "Cyan": 1660.7488200000003,
    "Magenta": 1709.5669699999994,
    "Yellow": 1559.69331,
    "Black": 1692.4901900000004
  },
  {
    "Date": "2021/Nov/20",
    "Cyan": 1940.99092,
    "Magenta": 2197.579899999999,
    "Yellow": 2114.55867,
    "Black": 1883.81579
  },
  {
    "Date": "2021/Nov/21",
    "Cyan": 1663.5761700000003,
    "Magenta": 1581.02808,
    "Yellow": 1491.7929600000004,
    "Black": 1731.71876
  },
  {
    "Date": "2021/Nov/22",
    "Cyan": 1784.86491,
    "Magenta": 1853.7387299999994,
    "Yellow": 1939.5563799999995,
    "Black": 1728.7389800000003
  },
  {
    "Date": "2021/Nov/23",
    "Cyan": 1865.2230499999998,
    "Magenta": 2174.9778,
    "Yellow": 1905.4033300000003,
    "Black": 1991.8112600000004
  },
  {
    "Date": "2021/Nov/24",
    "Cyan": 1912.7874199999999,
    "Magenta": 2162.673500000001,
    "Yellow": 1874.8474999999999,
    "Black": 1770.42354
  },
  {
    "Date": "2021/Nov/25",
    "Cyan": 2057.52352,
    "Magenta": 1956.8611600000004,
    "Yellow": 1864.4948200000001,
    "Black": 1914.22093
  },
  {
    "Date": "2021/Nov/26",
    "Cyan": 1427.2641500000002,
    "Magenta": 1914.4644199999998,
    "Yellow": 1757.866809999999,
    "Black": 1521.7142599999997
  },
  {
    "Date": "2021/Nov/27",
    "Cyan": 1873.5891100000006,
    "Magenta": 1869.8054099999995,
    "Yellow": 1833.77769,
    "Black": 1993.3736100000003
  },
  {
    "Date": "2021/Nov/28",
    "Cyan": 1720.30796,
    "Magenta": 1669.2052200000003,
    "Yellow": 1922.9127700000004,
    "Black": 1846.9796599999997
  },
  {
    "Date": "2021/Nov/29",
    "Cyan": 1647.6606100000004,
    "Magenta": 1951.2960799999996,
    "Yellow": 1759.6735399999993,
    "Black": 1966.3712200000002
  },
  {
    "Date": "2021/Nov/30",
    "Cyan": 1716.0897700000003,
    "Magenta": 1824.13732,
    "Yellow": 1944.2641500000002,
    "Black": 1826.4155900000005
  }
];

const topMachinesWithMostPrintVolumeSampleData = [
  {
    "Printer id": "706",
    "Printed square meters": 21062.539999999997
  },
  {
    "Printer id": "710",
    "Printed square meters": 19299.49
  },
  {
    "Printer id": "703",
    "Printed square meters": 19267.049999999996
  },
  {
    "Printer id": "704",
    "Printed square meters": 18945.55
  },
  {
    "Printer id": "701",
    "Printed square meters": 18652.280000000002
  },
  {
    "Printer id": "702",
    "Printed square meters": 18505.4
  },
  {
    "Printer id": "709",
    "Printed square meters": 18475.209999999995
  },
  {
    "Printer id": "705",
    "Printed square meters": 18218.08
  },
  {
    "Printer id": "708",
    "Printed square meters": 13734.470000000001
  },
  {
    "Printer id": "707",
    "Printed square meters": 13412.900000000001
  },
  {
    "Printer id": "700",
    "Printed square meters": 12015.350000000002
  }
];

const mediaTypesPerMachineSampleData = [
  {
    "Media type": "Oneway",
    "Printed square meters": 8.0
  },
  {
    "Media type": "Trasparente",
    "Printed square meters": 7.5
  },
  {
    "Media type": "EPS Canvas",
    "Printed square meters": 5.0
  },
  {
    "Media type": "Monomerico Retro Grigio",
    "Printed square meters": 4.0
  },
  {
    "Media type": "Creative smooth 320",
    "Printed square meters": 3.0
  },
  {
    "Media type": "Backfilm SP Midia",
    "Printed square meters": 2.0
  }
];

const mediaCategoryUsageSampleData = [
  {
    "Date": "2021/Dec/01",
    "Media category": "Canvas",
    "Printed square meters": 269.19741999999997
  },
  {
    "Date": "2021/Dec/01",
    "Media category": "Film",
    "Printed square meters": 139.38236
  },
  {
    "Date": "2021/Dec/01",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 183.87805999999998
  },
  {
    "Date": "2021/Dec/01",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 171.75941
  },
  {
    "Date": "2021/Dec/01",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 161.05451
  },
  {
    "Date": "2021/Dec/01",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 35.90245
  },
  {
    "Date": "2021/Dec/01",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 134.05659000000003
  },
  {
    "Date": "2021/Dec/01",
    "Media category": "Paper",
    "Printed square meters": 178.73844
  },
  {
    "Date": "2021/Dec/01",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 210.53147
  },
  {
    "Date": "2021/Dec/01",
    "Media category": "Textile",
    "Printed square meters": 205.86928999999998
  },
  {
    "Date": "2021/Dec/01",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 329.38743
  },
  {
    "Date": "2021/Dec/02",
    "Media category": "Canvas",
    "Printed square meters": 60.06326
  },
  {
    "Date": "2021/Dec/02",
    "Media category": "Film",
    "Printed square meters": 226.63088
  },
  {
    "Date": "2021/Dec/02",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 191.68811999999997
  },
  {
    "Date": "2021/Dec/02",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 169.79533
  },
  {
    "Date": "2021/Dec/02",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 418.33344000000005
  },
  {
    "Date": "2021/Dec/02",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 219.07285000000005
  },
  {
    "Date": "2021/Dec/02",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 279.23948
  },
  {
    "Date": "2021/Dec/02",
    "Media category": "Paper",
    "Printed square meters": 96.43527
  },
  {
    "Date": "2021/Dec/02",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 201.65959000000004
  },
  {
    "Date": "2021/Dec/02",
    "Media category": "Textile",
    "Printed square meters": 66.28694999999999
  },
  {
    "Date": "2021/Dec/02",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 65.20058
  },
  {
    "Date": "2021/Dec/03",
    "Media category": "Canvas",
    "Printed square meters": 130.79868
  },
  {
    "Date": "2021/Dec/03",
    "Media category": "Film",
    "Printed square meters": 210.88369000000003
  },
  {
    "Date": "2021/Dec/03",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 118.90950000000001
  },
  {
    "Date": "2021/Dec/03",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 131.19010000000003
  },
  {
    "Date": "2021/Dec/03",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 247.44669
  },
  {
    "Date": "2021/Dec/03",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 387.69277
  },
  {
    "Date": "2021/Dec/03",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 259.62338
  },
  {
    "Date": "2021/Dec/03",
    "Media category": "Paper",
    "Printed square meters": 185.1933
  },
  {
    "Date": "2021/Dec/03",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 233.50960999999998
  },
  {
    "Date": "2021/Dec/03",
    "Media category": "Textile",
    "Printed square meters": 208.91918999999996
  },
  {
    "Date": "2021/Dec/03",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 292.80354
  },
  {
    "Date": "2021/Dec/04",
    "Media category": "Canvas",
    "Printed square meters": 267.42255
  },
  {
    "Date": "2021/Dec/04",
    "Media category": "Film",
    "Printed square meters": 99.47608
  },
  {
    "Date": "2021/Dec/04",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 264.67494999999997
  },
  {
    "Date": "2021/Dec/04",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 73.56934000000001
  },
  {
    "Date": "2021/Dec/04",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 138.70485000000002
  },
  {
    "Date": "2021/Dec/04",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 225.02177
  },
  {
    "Date": "2021/Dec/04",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 150.51048
  },
  {
    "Date": "2021/Dec/04",
    "Media category": "Paper",
    "Printed square meters": 166.42677
  },
  {
    "Date": "2021/Dec/04",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 104.47022000000001
  },
  {
    "Date": "2021/Dec/04",
    "Media category": "Textile",
    "Printed square meters": 167.59289
  },
  {
    "Date": "2021/Dec/04",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 201.12186
  },
  {
    "Date": "2021/Dec/05",
    "Media category": "Canvas",
    "Printed square meters": 191.74504999999996
  },
  {
    "Date": "2021/Dec/05",
    "Media category": "Film",
    "Printed square meters": 163.07726
  },
  {
    "Date": "2021/Dec/05",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 114.05404000000001
  },
  {
    "Date": "2021/Dec/05",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 207.16309
  },
  {
    "Date": "2021/Dec/05",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 196.54981
  },
  {
    "Date": "2021/Dec/05",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 141.96621
  },
  {
    "Date": "2021/Dec/05",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 166.2236
  },
  {
    "Date": "2021/Dec/05",
    "Media category": "Paper",
    "Printed square meters": 83.31374
  },
  {
    "Date": "2021/Dec/05",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 159.03126
  },
  {
    "Date": "2021/Dec/05",
    "Media category": "Textile",
    "Printed square meters": 165.50770999999997
  },
  {
    "Date": "2021/Dec/05",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 145.36784
  },
  {
    "Date": "2021/Dec/06",
    "Media category": "Canvas",
    "Printed square meters": 137.10819
  },
  {
    "Date": "2021/Dec/06",
    "Media category": "Film",
    "Printed square meters": 210.84983000000003
  },
  {
    "Date": "2021/Dec/06",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 93.41502
  },
  {
    "Date": "2021/Dec/06",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 298.23246
  },
  {
    "Date": "2021/Dec/06",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 122.29392999999999
  },
  {
    "Date": "2021/Dec/06",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 114.27521
  },
  {
    "Date": "2021/Dec/06",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 244.75995
  },
  {
    "Date": "2021/Dec/06",
    "Media category": "Paper",
    "Printed square meters": 58.854200000000006
  },
  {
    "Date": "2021/Dec/06",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 167.33173000000002
  },
  {
    "Date": "2021/Dec/06",
    "Media category": "Textile",
    "Printed square meters": 126.73097999999999
  },
  {
    "Date": "2021/Dec/06",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 226.35893000000004
  },
  {
    "Date": "2021/Nov/20",
    "Media category": "Canvas",
    "Printed square meters": 181.37536
  },
  {
    "Date": "2021/Nov/20",
    "Media category": "Film",
    "Printed square meters": 226.98185
  },
  {
    "Date": "2021/Nov/20",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 245.02954999999997
  },
  {
    "Date": "2021/Nov/20",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 72.64053000000001
  },
  {
    "Date": "2021/Nov/20",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 53.18759
  },
  {
    "Date": "2021/Nov/20",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 75.16926000000001
  },
  {
    "Date": "2021/Nov/20",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 241.21667000000002
  },
  {
    "Date": "2021/Nov/20",
    "Media category": "Paper",
    "Printed square meters": 12.90009
  },
  {
    "Date": "2021/Nov/20",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 95.43490999999999
  },
  {
    "Date": "2021/Nov/20",
    "Media category": "Textile",
    "Printed square meters": 179.21204
  },
  {
    "Date": "2021/Nov/20",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 87.90143
  },
  {
    "Date": "2021/Nov/21",
    "Media category": "Canvas",
    "Printed square meters": 63.922689999999996
  },
  {
    "Date": "2021/Nov/21",
    "Media category": "Film",
    "Printed square meters": 192.89047
  },
  {
    "Date": "2021/Nov/21",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 171.01617
  },
  {
    "Date": "2021/Nov/21",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 172.35760000000002
  },
  {
    "Date": "2021/Nov/21",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 159.62809
  },
  {
    "Date": "2021/Nov/21",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 187.16475000000003
  },
  {
    "Date": "2021/Nov/21",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 106.4573
  },
  {
    "Date": "2021/Nov/21",
    "Media category": "Paper",
    "Printed square meters": 118.21345999999998
  },
  {
    "Date": "2021/Nov/21",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 91.61791
  },
  {
    "Date": "2021/Nov/21",
    "Media category": "Textile",
    "Printed square meters": 266.64877
  },
  {
    "Date": "2021/Nov/21",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 11.16552
  },
  {
    "Date": "2021/Nov/22",
    "Media category": "Canvas",
    "Printed square meters": 143.85687000000001
  },
  {
    "Date": "2021/Nov/22",
    "Media category": "Film",
    "Printed square meters": 187.80827000000002
  },
  {
    "Date": "2021/Nov/22",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 181.98952
  },
  {
    "Date": "2021/Nov/22",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 242.27409
  },
  {
    "Date": "2021/Nov/22",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 96.2235
  },
  {
    "Date": "2021/Nov/22",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 77.83576
  },
  {
    "Date": "2021/Nov/22",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 330.41051000000004
  },
  {
    "Date": "2021/Nov/22",
    "Media category": "Paper",
    "Printed square meters": 219.13294000000002
  },
  {
    "Date": "2021/Nov/22",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 201.93775
  },
  {
    "Date": "2021/Nov/22",
    "Media category": "Textile",
    "Printed square meters": 198.79742
  },
  {
    "Date": "2021/Nov/22",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 102.28236
  },
  {
    "Date": "2021/Nov/23",
    "Media category": "Canvas",
    "Printed square meters": 197.68457999999998
  },
  {
    "Date": "2021/Nov/23",
    "Media category": "Film",
    "Printed square meters": 39.945080000000004
  },
  {
    "Date": "2021/Nov/23",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 240.75948999999997
  },
  {
    "Date": "2021/Nov/23",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 212.44768
  },
  {
    "Date": "2021/Nov/23",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 71.86799
  },
  {
    "Date": "2021/Nov/23",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 166.44309
  },
  {
    "Date": "2021/Nov/23",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 331.38268
  },
  {
    "Date": "2021/Nov/23",
    "Media category": "Paper",
    "Printed square meters": 105.48647000000001
  },
  {
    "Date": "2021/Nov/23",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 170.56167
  },
  {
    "Date": "2021/Nov/23",
    "Media category": "Textile",
    "Printed square meters": 101.79988
  },
  {
    "Date": "2021/Nov/23",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 108.35955999999999
  },
  {
    "Date": "2021/Nov/24",
    "Media category": "Canvas",
    "Printed square meters": 113.88609
  },
  {
    "Date": "2021/Nov/24",
    "Media category": "Film",
    "Printed square meters": 218.42123
  },
  {
    "Date": "2021/Nov/24",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 127.34117
  },
  {
    "Date": "2021/Nov/24",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 141.77229
  },
  {
    "Date": "2021/Nov/24",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 439.90700000000004
  },
  {
    "Date": "2021/Nov/24",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 181.91737
  },
  {
    "Date": "2021/Nov/24",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 223.79850000000002
  },
  {
    "Date": "2021/Nov/24",
    "Media category": "Paper",
    "Printed square meters": 187.82878
  },
  {
    "Date": "2021/Nov/24",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 241.58085
  },
  {
    "Date": "2021/Nov/24",
    "Media category": "Textile",
    "Printed square meters": 99.97448
  },
  {
    "Date": "2021/Nov/24",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 166.40831000000003
  },
  {
    "Date": "2021/Nov/25",
    "Media category": "Canvas",
    "Printed square meters": 72.13101
  },
  {
    "Date": "2021/Nov/25",
    "Media category": "Film",
    "Printed square meters": 160.20502
  },
  {
    "Date": "2021/Nov/25",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 241.53801000000004
  },
  {
    "Date": "2021/Nov/25",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 117.41682000000002
  },
  {
    "Date": "2021/Nov/25",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 116.68027000000001
  },
  {
    "Date": "2021/Nov/25",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 367.26883
  },
  {
    "Date": "2021/Nov/25",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 167.29646
  },
  {
    "Date": "2021/Nov/25",
    "Media category": "Paper",
    "Printed square meters": 166.31239000000002
  },
  {
    "Date": "2021/Nov/25",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 215.35320000000002
  },
  {
    "Date": "2021/Nov/25",
    "Media category": "Textile",
    "Printed square meters": 249.00348
  },
  {
    "Date": "2021/Nov/25",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 186.68888
  },
  {
    "Date": "2021/Nov/26",
    "Media category": "Canvas",
    "Printed square meters": 221.81744
  },
  {
    "Date": "2021/Nov/26",
    "Media category": "Film",
    "Printed square meters": 198.36293999999998
  },
  {
    "Date": "2021/Nov/26",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 203.23385000000002
  },
  {
    "Date": "2021/Nov/26",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 98.23931999999999
  },
  {
    "Date": "2021/Nov/26",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 104.01510999999999
  },
  {
    "Date": "2021/Nov/26",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 41.61479
  },
  {
    "Date": "2021/Nov/26",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 176.3043
  },
  {
    "Date": "2021/Nov/26",
    "Media category": "Paper",
    "Printed square meters": 211.85311
  },
  {
    "Date": "2021/Nov/26",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 157.25443
  },
  {
    "Date": "2021/Nov/26",
    "Media category": "Textile",
    "Printed square meters": 140.40148
  },
  {
    "Date": "2021/Nov/26",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 164.86622999999997
  },
  {
    "Date": "2021/Nov/27",
    "Media category": "Canvas",
    "Printed square meters": 218.02438
  },
  {
    "Date": "2021/Nov/27",
    "Media category": "Film",
    "Printed square meters": 114.93493999999998
  },
  {
    "Date": "2021/Nov/27",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 289.57432
  },
  {
    "Date": "2021/Nov/27",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 149.42245
  },
  {
    "Date": "2021/Nov/27",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 81.29102
  },
  {
    "Date": "2021/Nov/27",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 201.77917
  },
  {
    "Date": "2021/Nov/27",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 166.12202
  },
  {
    "Date": "2021/Nov/27",
    "Media category": "Paper",
    "Printed square meters": 118.91779000000002
  },
  {
    "Date": "2021/Nov/27",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 192.90021
  },
  {
    "Date": "2021/Nov/27",
    "Media category": "Textile",
    "Printed square meters": 333.07425
  },
  {
    "Date": "2021/Nov/27",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 97.55603999999998
  },
  {
    "Date": "2021/Nov/28",
    "Media category": "Canvas",
    "Printed square meters": 223.14779999999996
  },
  {
    "Date": "2021/Nov/28",
    "Media category": "Film",
    "Printed square meters": 169.46355
  },
  {
    "Date": "2021/Nov/28",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 208.57626
  },
  {
    "Date": "2021/Nov/28",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 184.99215
  },
  {
    "Date": "2021/Nov/28",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 112.31069
  },
  {
    "Date": "2021/Nov/28",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 115.94884
  },
  {
    "Date": "2021/Nov/28",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 173.77973000000003
  },
  {
    "Date": "2021/Nov/28",
    "Media category": "Paper",
    "Printed square meters": 219.81072
  },
  {
    "Date": "2021/Nov/28",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 257.32671
  },
  {
    "Date": "2021/Nov/28",
    "Media category": "Textile",
    "Printed square meters": 98.81199000000001
  },
  {
    "Date": "2021/Nov/28",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 233.66136
  },
  {
    "Date": "2021/Nov/29",
    "Media category": "Canvas",
    "Printed square meters": 95.09819999999999
  },
  {
    "Date": "2021/Nov/29",
    "Media category": "Film",
    "Printed square meters": 96.91024999999999
  },
  {
    "Date": "2021/Nov/29",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 190.19635
  },
  {
    "Date": "2021/Nov/29",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 179.97588
  },
  {
    "Date": "2021/Nov/29",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 144.29503
  },
  {
    "Date": "2021/Nov/29",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 92.41524000000001
  },
  {
    "Date": "2021/Nov/29",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 134.1261
  },
  {
    "Date": "2021/Nov/29",
    "Media category": "Paper",
    "Printed square meters": 196.30129999999997
  },
  {
    "Date": "2021/Nov/29",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 166.53446
  },
  {
    "Date": "2021/Nov/29",
    "Media category": "Textile",
    "Printed square meters": 220.69386000000003
  },
  {
    "Date": "2021/Nov/29",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 264.93449
  },
  {
    "Date": "2021/Nov/30",
    "Media category": "Canvas",
    "Printed square meters": 143.64485000000002
  },
  {
    "Date": "2021/Nov/30",
    "Media category": "Film",
    "Printed square meters": 65.46329
  },
  {
    "Date": "2021/Nov/30",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 142.35277000000002
  },
  {
    "Date": "2021/Nov/30",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 217.81156
  },
  {
    "Date": "2021/Nov/30",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 134.8678
  },
  {
    "Date": "2021/Nov/30",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 138.02331999999998
  },
  {
    "Date": "2021/Nov/30",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 295.07178
  },
  {
    "Date": "2021/Nov/30",
    "Media category": "Paper",
    "Printed square meters": 295.72556
  },
  {
    "Date": "2021/Nov/30",
    "Media category": "Textile",
    "Printed square meters": 113.79588
  },
  {
    "Date": "2021/Nov/30",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 224.89475000000004
  }
];

const squareMetersPerPrintModeSampleData = [
  {
    "Date": "2021/Dec/01",
    "Print mode": "Backlit",
    "Printed square meters": 253.73
  },
  {
    "Date": "2021/Dec/01",
    "Print mode": "High quality",
    "Printed square meters": 39.38
  },
  {
    "Date": "2021/Dec/01",
    "Print mode": "High speed",
    "Printed square meters": 1548.87
  },
  {
    "Date": "2021/Dec/01",
    "Print mode": "Max speed",
    "Printed square meters": 1522.99
  },
  {
    "Date": "2021/Dec/01",
    "Print mode": "Other",
    "Printed square meters": 844.81
  },
  {
    "Date": "2021/Dec/01",
    "Print mode": "Production",
    "Printed square meters": 2515.67
  },
  {
    "Date": "2021/Dec/01",
    "Print mode": "Reliance",
    "Printed square meters": 1169.75
  },
  {
    "Date": "2021/Dec/01",
    "Print mode": "Specialty",
    "Printed square meters": 1436.46
  },
  {
    "Date": "2021/Dec/02",
    "Print mode": "Backlit",
    "Printed square meters": 926.6
  },
  {
    "Date": "2021/Dec/02",
    "Print mode": "High quality",
    "Printed square meters": 567.4
  },
  {
    "Date": "2021/Dec/02",
    "Print mode": "Other",
    "Printed square meters": 1542.51
  },
  {
    "Date": "2021/Dec/02",
    "Print mode": "Production",
    "Printed square meters": 345.05
  },
  {
    "Date": "2021/Dec/03",
    "Print mode": "Backlit",
    "Printed square meters": 850.68
  },
  {
    "Date": "2021/Dec/03",
    "Print mode": "High quality",
    "Printed square meters": 1354.65
  },
  {
    "Date": "2021/Dec/03",
    "Print mode": "Max speed",
    "Printed square meters": 1532.5300000000002
  },
  {
    "Date": "2021/Dec/03",
    "Print mode": "Other",
    "Printed square meters": 759.36
  },
  {
    "Date": "2021/Dec/03",
    "Print mode": "Reliance",
    "Printed square meters": 1449.94
  },
  {
    "Date": "2021/Dec/03",
    "Print mode": "Specialty",
    "Printed square meters": 885.3
  },
  {
    "Date": "2021/Dec/04",
    "Print mode": "Backlit",
    "Printed square meters": 2181.84
  },
  {
    "Date": "2021/Dec/04",
    "Print mode": "High quality",
    "Printed square meters": 508.82
  },
  {
    "Date": "2021/Dec/04",
    "Print mode": "Max speed",
    "Printed square meters": 537.9
  },
  {
    "Date": "2021/Dec/04",
    "Print mode": "Other",
    "Printed square meters": 2478.9800000000005
  },
  {
    "Date": "2021/Dec/04",
    "Print mode": "Reliance",
    "Printed square meters": 931.38
  },
  {
    "Date": "2021/Dec/04",
    "Print mode": "Specialty",
    "Printed square meters": 718.33
  },
  {
    "Date": "2021/Dec/05",
    "Print mode": "Backlit",
    "Printed square meters": 1277.4299999999998
  },
  {
    "Date": "2021/Dec/05",
    "Print mode": "High quality",
    "Printed square meters": 752.8299999999999
  },
  {
    "Date": "2021/Dec/05",
    "Print mode": "High speed",
    "Printed square meters": 960.47
  },
  {
    "Date": "2021/Dec/05",
    "Print mode": "Max speed",
    "Printed square meters": 683.35
  },
  {
    "Date": "2021/Dec/05",
    "Print mode": "Other",
    "Printed square meters": 835.08
  },
  {
    "Date": "2021/Dec/05",
    "Print mode": "Reliance",
    "Printed square meters": 1017.71
  },
  {
    "Date": "2021/Dec/05",
    "Print mode": "Specialty",
    "Printed square meters": 914.99
  },
  {
    "Date": "2021/Dec/06",
    "Print mode": "High quality",
    "Printed square meters": 584.39
  },
  {
    "Date": "2021/Dec/06",
    "Print mode": "Max speed",
    "Printed square meters": 1234.03
  },
  {
    "Date": "2021/Dec/06",
    "Print mode": "Reliance",
    "Printed square meters": 1382.08
  },
  {
    "Date": "2021/Dec/06",
    "Print mode": "Specialty",
    "Printed square meters": 437.85
  },
  {
    "Date": "2021/Dec/07",
    "Print mode": "Backlit",
    "Printed square meters": 1471.38
  },
  {
    "Date": "2021/Dec/07",
    "Print mode": "High quality",
    "Printed square meters": 22.71
  },
  {
    "Date": "2021/Dec/07",
    "Print mode": "High speed",
    "Printed square meters": 2088.89
  },
  {
    "Date": "2021/Dec/07",
    "Print mode": "Max speed",
    "Printed square meters": 1740.24
  },
  {
    "Date": "2021/Dec/07",
    "Print mode": "Other",
    "Printed square meters": 873.58
  },
  {
    "Date": "2021/Dec/07",
    "Print mode": "Production",
    "Printed square meters": 1478.63
  },
  {
    "Date": "2021/Dec/07",
    "Print mode": "Reliance",
    "Printed square meters": 960.15
  },
  {
    "Date": "2021/Dec/07",
    "Print mode": "Specialty",
    "Printed square meters": 3400.0800000000004
  },
  {
    "Date": "2021/Nov/20",
    "Print mode": "High quality",
    "Printed square meters": 811.04
  },
  {
    "Date": "2021/Nov/20",
    "Print mode": "High speed",
    "Printed square meters": 991.99
  },
  {
    "Date": "2021/Nov/20",
    "Print mode": "Reliance",
    "Printed square meters": 482.53
  },
  {
    "Date": "2021/Nov/20",
    "Print mode": "Specialty",
    "Printed square meters": 67.02
  },
  {
    "Date": "2021/Nov/21",
    "Print mode": "High quality",
    "Printed square meters": 1462.63
  },
  {
    "Date": "2021/Nov/21",
    "Print mode": "High speed",
    "Printed square meters": 466.81
  },
  {
    "Date": "2021/Nov/21",
    "Print mode": "Other",
    "Printed square meters": 938.92
  },
  {
    "Date": "2021/Nov/21",
    "Print mode": "Reliance",
    "Printed square meters": 481.52000000000004
  },
  {
    "Date": "2021/Nov/21",
    "Print mode": "Specialty",
    "Printed square meters": 931.67
  },
  {
    "Date": "2021/Nov/22",
    "Print mode": "Backlit",
    "Printed square meters": 772.63
  },
  {
    "Date": "2021/Nov/22",
    "Print mode": "High quality",
    "Printed square meters": 944.1700000000001
  },
  {
    "Date": "2021/Nov/22",
    "Print mode": "High speed",
    "Printed square meters": 427.96
  },
  {
    "Date": "2021/Nov/22",
    "Print mode": "Max speed",
    "Printed square meters": 782.55
  },
  {
    "Date": "2021/Nov/22",
    "Print mode": "Production",
    "Printed square meters": 432.51
  },
  {
    "Date": "2021/Nov/22",
    "Print mode": "Reliance",
    "Printed square meters": 1387.67
  },
  {
    "Date": "2021/Nov/23",
    "Print mode": "Backlit",
    "Printed square meters": 1129.37
  },
  {
    "Date": "2021/Nov/23",
    "Print mode": "High quality",
    "Printed square meters": 994.68
  },
  {
    "Date": "2021/Nov/23",
    "Print mode": "High speed",
    "Printed square meters": 914.3599999999999
  },
  {
    "Date": "2021/Nov/23",
    "Print mode": "Max speed",
    "Printed square meters": 2930.3199999999997
  },
  {
    "Date": "2021/Nov/23",
    "Print mode": "Other",
    "Printed square meters": 898.58
  },
  {
    "Date": "2021/Nov/23",
    "Print mode": "Production",
    "Printed square meters": 739
  },
  {
    "Date": "2021/Nov/23",
    "Print mode": "Reliance",
    "Printed square meters": 527.1
  },
  {
    "Date": "2021/Nov/24",
    "Print mode": "Backlit",
    "Printed square meters": 1395.46
  },
  {
    "Date": "2021/Nov/24",
    "Print mode": "High quality",
    "Printed square meters": 186.53
  },
  {
    "Date": "2021/Nov/24",
    "Print mode": "High speed",
    "Printed square meters": 1493.3
  },
  {
    "Date": "2021/Nov/24",
    "Print mode": "Max speed",
    "Printed square meters": 288.3
  },
  {
    "Date": "2021/Nov/24",
    "Print mode": "Other",
    "Printed square meters": 433.04
  },
  {
    "Date": "2021/Nov/24",
    "Print mode": "Production",
    "Printed square meters": 927.03
  },
  {
    "Date": "2021/Nov/24",
    "Print mode": "Reliance",
    "Printed square meters": 829.7
  },
  {
    "Date": "2021/Nov/24",
    "Print mode": "Specialty",
    "Printed square meters": 184.57
  },
  {
    "Date": "2021/Nov/25",
    "Print mode": "Backlit",
    "Printed square meters": 1029.02
  },
  {
    "Date": "2021/Nov/25",
    "Print mode": "High quality",
    "Printed square meters": 936.91
  },
  {
    "Date": "2021/Nov/25",
    "Print mode": "Max speed",
    "Printed square meters": 920.65
  },
  {
    "Date": "2021/Nov/25",
    "Print mode": "Other",
    "Printed square meters": 756.62
  },
  {
    "Date": "2021/Nov/25",
    "Print mode": "Production",
    "Printed square meters": 793.38
  },
  {
    "Date": "2021/Nov/25",
    "Print mode": "Reliance",
    "Printed square meters": 1287.3200000000002
  },
  {
    "Date": "2021/Nov/25",
    "Print mode": "Specialty",
    "Printed square meters": 638.67
  },
  {
    "Date": "2021/Nov/26",
    "Print mode": "Backlit",
    "Printed square meters": 55.95
  },
  {
    "Date": "2021/Nov/26",
    "Print mode": "High quality",
    "Printed square meters": 1063.06
  },
  {
    "Date": "2021/Nov/26",
    "Print mode": "High speed",
    "Printed square meters": 748.78
  },
  {
    "Date": "2021/Nov/26",
    "Print mode": "Max speed",
    "Printed square meters": 1326.41
  },
  {
    "Date": "2021/Nov/26",
    "Print mode": "Specialty",
    "Printed square meters": 1083.7
  },
  {
    "Date": "2021/Nov/27",
    "Print mode": "Backlit",
    "Printed square meters": 38.34
  },
  {
    "Date": "2021/Nov/27",
    "Print mode": "High quality",
    "Printed square meters": 1532.46
  },
  {
    "Date": "2021/Nov/27",
    "Print mode": "High speed",
    "Printed square meters": 990.83
  },
  {
    "Date": "2021/Nov/27",
    "Print mode": "Max speed",
    "Printed square meters": 300.96
  },
  {
    "Date": "2021/Nov/27",
    "Print mode": "Other",
    "Printed square meters": 1508.82
  },
  {
    "Date": "2021/Nov/27",
    "Print mode": "Production",
    "Printed square meters": 1756.5
  },
  {
    "Date": "2021/Nov/27",
    "Print mode": "Specialty",
    "Printed square meters": 955.59
  },
  {
    "Date": "2021/Nov/28",
    "Print mode": "Backlit",
    "Printed square meters": 2462.24
  },
  {
    "Date": "2021/Nov/28",
    "Print mode": "High quality",
    "Printed square meters": 2480.45
  },
  {
    "Date": "2021/Nov/28",
    "Print mode": "High speed",
    "Printed square meters": 2635.36
  },
  {
    "Date": "2021/Nov/28",
    "Print mode": "Max speed",
    "Printed square meters": 1857.8899999999999
  },
  {
    "Date": "2021/Nov/28",
    "Print mode": "Other",
    "Printed square meters": 345.24
  },
  {
    "Date": "2021/Nov/28",
    "Print mode": "Reliance",
    "Printed square meters": 340.18
  },
  {
    "Date": "2021/Nov/28",
    "Print mode": "Specialty",
    "Printed square meters": 2832.54
  },
  {
    "Date": "2021/Nov/29",
    "Print mode": "Backlit",
    "Printed square meters": 1549.17
  },
  {
    "Date": "2021/Nov/29",
    "Print mode": "High quality",
    "Printed square meters": 1134.67
  },
  {
    "Date": "2021/Nov/29",
    "Print mode": "High speed",
    "Printed square meters": 1826.57
  },
  {
    "Date": "2021/Nov/29",
    "Print mode": "Max speed",
    "Printed square meters": 1140.15
  },
  {
    "Date": "2021/Nov/29",
    "Print mode": "Other",
    "Printed square meters": 1852.2199999999998
  },
  {
    "Date": "2021/Nov/29",
    "Print mode": "Production",
    "Printed square meters": 2337.12
  },
  {
    "Date": "2021/Nov/29",
    "Print mode": "Reliance",
    "Printed square meters": 438.34
  },
  {
    "Date": "2021/Nov/29",
    "Print mode": "Specialty",
    "Printed square meters": 627
  },
  {
    "Date": "2021/Nov/30",
    "Print mode": "Backlit",
    "Printed square meters": 945.78
  },
  {
    "Date": "2021/Nov/30",
    "Print mode": "High quality",
    "Printed square meters": 441.01
  },
  {
    "Date": "2021/Nov/30",
    "Print mode": "High speed",
    "Printed square meters": 992.5
  },
  {
    "Date": "2021/Nov/30",
    "Print mode": "Max speed",
    "Printed square meters": 766.96
  },
  {
    "Date": "2021/Nov/30",
    "Print mode": "Other",
    "Printed square meters": 971.32
  },
  {
    "Date": "2021/Nov/30",
    "Print mode": "Production",
    "Printed square meters": 1545.84
  },
  {
    "Date": "2021/Nov/30",
    "Print mode": "Reliance",
    "Printed square meters": 362.72
  }
];

export { inkUsageSampleData, mediaCategoryUsageSampleData, topMachinesWithMostPrintVolumeSampleData, mediaTypesPerMachineSampleData, squareMetersPerPrintModeSampleData };