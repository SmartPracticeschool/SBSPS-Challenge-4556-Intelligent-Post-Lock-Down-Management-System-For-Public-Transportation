let areas = [
  {
    _id: "entrance",
    name: "Entrance",
    cameras: [
      {
        _id: "entrance_camera1",
        name: "Camera 1",
        ip: "#",
        data: {
          instant: {
            crowdDensity: 20,
            distanceViolations: 5,
            maskViolations: 3,
          },
          avg: {
            crowdDensity: 18,
            distanceViolations: 8,
            maskViolations: 5,
          },
        },
      },
      {
        _id: "entrance_camera2",
        name: "Camera 2",
        ip: "#",
        data: {
          instant: {
            crowdDensity: 10,
            distanceViolations: 5,
            maskViolations: 1,
          },
          avg: {
            crowdDensity: 19,
            distanceViolations: 8,
            maskViolations: 3,
          },
        },
      },
    ],
  },
  {
    _id: "lobby",
    name: "Lobby",
    cameras: [
      {
        _id: "lobby_camera1",
        name: "Camera 1",
        ip: "#",
        data: {
          instant: {
            crowdDensity: 26,
            distanceViolations: 7,
            maskViolations: 0,
          },
          avg: {
            crowdDensity: 33,
            distanceViolations: 10,
            maskViolations: 2,
          },
        },
      },
      {
        _id: "lobby_camera2",
        name: "Camera 2",
        ip: "#",
        data: {
          instant: {
            crowdDensity: 20,
            distanceViolations: 5,
            maskViolations: 0,
          },
          avg: {
            crowdDensity: 15,
            distanceViolations: 3,
            maskViolations: 5,
          },
        },
      },
    ],
  },
];

const addCamera = (cam) => {
  const cam_id =
    cam.area.split(" ").join("").toLowerCase() +
    "_" +
    cam.camname.split(" ").join("").toLowerCase();
  const area_id = cam.area.split(" ").join("").toLowerCase();

  const ar_ind = areas.findIndex((ele) => ele._id === area_id);

  const cam_obj = { _id: cam_id, name: cam.camname, ip: cam.camip, data: null };

  //if area already exists in DB
  if (ar_ind !== -1) {
    const cam_ind = areas[ar_ind].cameras.findIndex(
      (ele) => ele.name === cam.camname
    );

    //camera of the same name exists in given area
    if (cam_ind !== -1)
      return {
        success: false,
        message: `${cam.camname} already exists in ${cam.area}`,
      };

    areas[ar_ind].cameras = [...areas[ar_ind].cameras, cam_obj];
  }

  //if cam.area doesn't exist already in DB
  else {
    areas = [
      ...areas,
      {
        _id: area_id,
        name: cam.area,
        cameras: [cam_obj],
      },
    ];
  }
  return {
    success: true,
    message: `${cam.camname} added in ${cam.area}`,
  };
};

export { areas, addCamera };


