import json

class Listing:
    id = 0
    campaign = False
    userProfileId = 0
    gameName = None
    environment = None
    day = None
    startTime = None
    endTime = None
    difficulty = None
    role = None

    def __init__(self, j) -> None:
        self.__dict__ = json.loads(j)
    
    def __eq__(self, rhs) -> bool:
        return self.listingId == rhs.listingId
    
    def __repr__(self) -> str:
        return (f'Id: {self.userProfileId}\n'+
                f'Game: {self.gameName}\n'+
                f'Campaign: {self.campaign}\n')
    
    def __str__(self) -> str:
        return (f'Id: {self.userProfileId}\n'+
                f'Game: {self.gameName}\n'+
                f'Environement: {self.environment}\n'+
                f'Schedule: {self.day} {self.startTime} - {self.endTime}\n'+
                f'Difficulty: {self.difficulty}\n'+
                f'Role: {self.role}\n')