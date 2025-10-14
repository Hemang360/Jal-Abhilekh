/**
 * Mock Dam entity for development
 */
class Dam {
  static async list() {
    // Return mock data for now
    return [
      { id: 1, name: 'Bhakra Dam', state: 'Himachal Pradesh', status: 'Safe' },
      { id: 2, name: 'Tehri Dam', state: 'Uttarakhand', status: 'Caution' },
      { id: 3, name: 'Sardar Sarovar Dam', state: 'Gujarat', status: 'Safe' },
      { id: 4, name: 'Hirakud Dam', state: 'Odisha', status: 'Critical' },
      { id: 5, name: 'Nagarjuna Sagar Dam', state: 'Telangana', status: 'Safe' },
    ];
  }
}

export { Dam };
