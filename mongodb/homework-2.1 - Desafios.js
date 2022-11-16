----------------------------------------------------


-- Insert de desafios:

-- InsertMany:

db.desafios.insertMany(
    [
    {
      "pergunta": "Goku é mais forte que Saitama ?",
      "resposta": 0,
      "idUsuario": 2,
      "_id": 1
    },
    {
      "pergunta": "Naruto é mais forte que o Goku?",
      "resposta": "n",
      "idUsuario": 3,
      "_id": 2
    },
    {
      "pergunta": "Naruto é mais fraco que o Saitama?",
      "resposta": "s",
      "idUsuario": 4,
      "_id": 3
          
    }
  ]
  )

----------------------------------------------------


-- find 1:

db.desafios.find(
    {
        "pergunta" : "Naruto é mais forte que o Goku?"
    }
)

-- log:

{ 
    _id: 2,
    pergunta: 'Naruto é mais forte que o Goku?',
    resposta: 'n',
    idUsuario: 3
 }

 --------------------------

 -- find 2:

 db.desafios.find(
    {
        "pergunta": "Goku é mais forte que Saitama ?"
    }
)

-- log:

{ 
    _id: 1,
    pergunta: 'Goku é mais forte que Saitama ?',
    resposta: 's',
    idUsuario: 2
 }

----------------------------------------------------


 -- update 1:

db.desafios.updateOne(
    { pergunta: "Goku é mais forte que Saitama ?" },
    {
        $set: { "pergunta": "Goku é mais forte que o Saitama? "}
    })

-- log:
{ acknowledged: true,
    insertedId: null,
    matchedCount: 1,
    modifiedCount: 1,
    upsertedCount: 0 }

--------------------------

-- update 2:

db.desafios.updateOne(
    { pergunta: "Naruto é mais forte que o Goku ?" },
    {
        $set: { "pergunta": "Naruto do Shippuden é mais forte que o Goku ?"}
    })

-- log:

{ acknowledged: true,
    insertedId: null,
    matchedCount: 0,
    modifiedCount: 0,
    upsertedCount: 0 }

----------------------------------------------------


-- Projection 1:

db.desafios.find({}, {_id: 0, "resposta": 1})

-- log:

{ _id: 1, resposta: 's' }
{ _id: 2, resposta: 'n' }

--------------------------

-- Projetciton 2:

db.desafios.find({}, 
    {
        pergunta_resposta: { $concat: [ "$pergunta", " = ", "$resposta"]}
    })

-- log:

{ _id: 1,
    pergunta_resposta: 'Goku é mais forte que o Saitama? = s' }
  { _id: 2,
    pergunta_resposta: 'Naruto é mais forte que o Goku? = n' }

--------------------------

-- Aggregate 1:

    db.desafios.aggregate( [
        { $match: { resposta: "s"} },
        { $group: {_id: "$pergunta"} }
    ])

-- log:

{ _id: 'Goku é mais forte que o Saitama?' }
{ _id: 'Naruto é mais fraco que o Saitama?' }

--------------------------

-- Aggregate 2:

db.desafios.aggregate( [
    { $match: { resposta: "n"} },
    { $group: {_id: "$pergunta"} }
])

-- log:

{ _id: 'Naruto é mais forte que o Goku?' }

----------------------------------------------------

-- Delete 1:

db.desafios.deleteOne(
    {
        "pergunta" : "Naruto é mais fraco que o Saitama?"
    }
)

-- log:

{ acknowledged: true, deletedCount: 1 }

--------------------------

-- Delete 2:

db.desafios.deleteOne(
    {
        "pergunta" : "Goku é mais forte que o Saitama? "
    }
)

-- log:

{ acknowledged: true, deletedCount: 0 }

----------------------------------------------------
