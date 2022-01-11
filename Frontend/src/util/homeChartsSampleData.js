
const inkUsageSampleData = [
  {
    'Date': '2021/Apr/01',
    'Cyan': 1771.3875600000003,
    'Magenta': 1896.0194099999994,
    'Yellow': 1754.2646200000006,
    'Black': 1883.3413000000005
  },
  {
    'Date': '2021/Apr/02',
    'Cyan': 1708.77815,
    'Magenta': 1880.6092900000003,
    'Yellow': 1877.16592,
    'Black': 1983.6693399999995
  },
  {
    'Date': '2021/Apr/03',
    'Cyan': 2021.68844,
    'Magenta': 1928.9640999999995,
    'Yellow': 1922.4390099999991,
    'Black': 1831.4986999999996
  }];

const topMachinesWithMostPrintVolumeSampleData = [
  {
    'Printer id': '701',
    'Printed square meters': 10.5
  },
  {
    'Printer id': '700',
    'Printed square meters': 6.5
  },
  {
    'Printer id': '702',
    'Printed square meters': 6.0
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
    "Date": "2021/Apr/01",
    "Media category": "Canvas",
    "Printed square meters": 184.53796999999997
  },
  {
    "Date": "2021/Apr/01",
    "Media category": "Film",
    "Printed square meters": 265.49472000000003
  },
  {
    "Date": "2021/Apr/01",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 66.29216
  },
  {
    "Date": "2021/Apr/01",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 146.39474
  },
  {
    "Date": "2021/Apr/01",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 164.40368
  },
  {
    "Date": "2021/Apr/01",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 238.66689
  },
  {
    "Date": "2021/Apr/01",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 137.63388999999998
  },
  {
    "Date": "2021/Apr/01",
    "Media category": "Paper",
    "Printed square meters": 108.07258
  },
  {
    "Date": "2021/Apr/01",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 92.00584
  },
  {
    "Date": "2021/Apr/01",
    "Media category": "Textile",
    "Printed square meters": 158.36604
  },
  {
    "Date": "2021/Apr/01",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 227.91973000000002
  },
  {
    "Date": "2021/Apr/02",
    "Media category": "Canvas",
    "Printed square meters": 233.76735000000002
  },
  {
    "Date": "2021/Apr/02",
    "Media category": "Film",
    "Printed square meters": 143.05799000000002
  },
  {
    "Date": "2021/Apr/02",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 148.98255
  },
  {
    "Date": "2021/Apr/02",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 166.64817000000002
  },
  {
    "Date": "2021/Apr/02",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 254.28093
  },
  {
    "Date": "2021/Apr/02",
    "Media category": "Light paper < 120gsm",
    "Printed square meters": 296.18193
  },
  {
    "Date": "2021/Apr/02",
    "Media category": "Monomeric vinyl",
    "Printed square meters": 244.58351999999996
  },
  {
    "Date": "2021/Apr/02",
    "Media category": "Paper",
    "Printed square meters": 211.46303
  },
  {
    "Date": "2021/Apr/02",
    "Media category": "Polymeric & cast vinyl",
    "Printed square meters": 354.44577000000004
  },
  {
    "Date": "2021/Apr/02",
    "Media category": "Textile",
    "Printed square meters": 202.83961999999997
  },
  {
    "Date": "2021/Apr/02",
    "Media category": "Thick film > 200 um",
    "Printed square meters": 211.58083
  },
  {
    "Date": "2021/Apr/03",
    "Media category": "Canvas",
    "Printed square meters": 141.23336
  },
  {
    "Date": "2021/Apr/03",
    "Media category": "Film",
    "Printed square meters": 176.31877
  },
  {
    "Date": "2021/Apr/03",
    "Media category": "Heavy Banner > 400gsm",
    "Printed square meters": 171.57962999999998
  },
  {
    "Date": "2021/Apr/03",
    "Media category": "Heavy paper > 200gsm",
    "Printed square meters": 121.9274
  },
  {
    "Date": "2021/Apr/03",
    "Media category": "Light Banner < 400gsm",
    "Printed square meters": 83.15289999999999
  }];

const squareMetersPerPrintModeSampleData = [
  {
    "Date": "2021/Dec/01",
    "Print mode": "Backlit",
    "Printed square meters": 1.5
  },
  {
    "Date": "2021/Dec/01",
    "Print mode": "High speed",
    "Printed square meters": 5.0
  },
  {
    "Date": "2021/Dec/01",
    "Print mode": "Production",
    "Printed square meters": 7.5
  },
  {
    "Date": "2021/Dec/01",
    "Print mode": "Specialty",
    "Printed square meters": 2.0
  },
  {
    "Date": "2021/Dec/02",
    "Print mode": "Backlit",
    "Printed square meters": 3.5
  },
  {
    "Date": "2021/Dec/02",
    "Print mode": "Reliance",
    "Printed square meters": 8.0
  },
  {
    "Date": "2021/Dec/02",
    "Print mode": "Specialty",
    "Printed square meters": 2.0
  }
];

export { inkUsageSampleData, mediaCategoryUsageSampleData, topMachinesWithMostPrintVolumeSampleData, mediaTypesPerMachineSampleData, squareMetersPerPrintModeSampleData };