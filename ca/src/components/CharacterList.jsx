import { useEffect, useState } from "react"
import { useDispatch, useSelector } from "react-redux"
import { getAllData } from "../store/action/characterAction"

function CharacterList() {

    const [currentPage, setCurrentPage] = useState(0)
    const dispatch = useDispatch()
    const { characters, totalPages } = useSelector(state => state.characters)

    let count = 0
    const arry = Array.from({ length: totalPages })

    useEffect(() => {
        dispatch(getAllData(currentPage))
    }, [currentPage])  

    return (
        <div>
            <h1>Rick and Morty Characters</h1>
            <table className="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Species</th>
                        <th>Origin</th>
                        <th>Location</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        characters.map((c, index) => (
                            <tr key={index}>
                                <td>{c.id}</td>
                                <td>{c.name}</td>
                                <td>{c.status}</td>
                                <td>{c.species}</td>
                                <td>{c.origin?.name}</td>
                                <td>{c.location?.name}</td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>

            <nav>
                <ul className="pagination justify-content-center">
                    <li className="page-item">
                        <button className="page-link" disabled={currentPage === 1}
                            onClick={() => setCurrentPage(currentPage - 1)}>Previous</button>
                    </li>
                    {
    arry.map((_, index) => {
        const pageNum = index + 1
        if (pageNum < currentPage - 2 || pageNum > currentPage + 2) return null
        return (
            <li className="page-item" key={index}>
                <button
                    className={`page-link ${currentPage === pageNum ? 'active' : ''}`}
                    onClick={() => setCurrentPage(pageNum)}>{pageNum}
                </button>
            </li>
        )
    })
}
                    <li className="page-item">
                        <button className="page-link" disabled={currentPage === totalPages}
                            onClick={() => setCurrentPage(currentPage + 1)}>Next</button>
                    </li>
                </ul>
            </nav>
        </div>
    )
}

export default CharacterList