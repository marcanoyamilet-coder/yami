package com.example.data.mock

data class Subject(val code: String, val name: String, val credits: Int)

data class Semester(val number: Int, val name: String, val subjects: List<Subject>)

data class Career(
    val id: String,
    val name: String,
    val degree: String,
    val duration: String,
    val description: String,
    val semesters: List<Semester>
)

data class CampusLocation(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val imageDescription: String
)

object UnimarData {
    private fun createSemesters(periodLists: List<List<String>>, periodType: String = "Periodo"): List<Semester> {
        return periodLists.mapIndexed { index, subjectNames ->
            Semester(
                number = index + 1,
                name = "$periodType ${index + 1}",
                subjects = subjectNames.mapIndexed { _, name ->
                    Subject(
                        code = "", // Empty to meet the "sin los códigos" request
                        name = name,
                        credits = if (name.contains("Deportivas") || name.contains("Dibujo") || name.contains("Laboratorio")) 1 
                                  else if (name.contains("Desarrollo") || name.contains("Derecho de Familia") || name.contains("Oratoria") || name.contains("Sociohumanística") || name.contains("Electiva Socio")) 2 
                                  else 3
                    )
                }
            )
        }
    }

    val careers = listOf(
        Career(
            id = "sistemas",
            name = "Ingeniería de Sistemas",
            degree = "Ingeniero de Sistemas",
            duration = "12 Trimestres",
            description = "Formamos profesionales capaces de modelar, diseñar, implementar y gestionar sistemas de información complejos, redes de telecomunicaciones y soluciones de software innovadoras, aplicando principios científicos y tecnológicos de vanguardia.",
            semesters = createSemesters(
                listOf(
                    listOf("Actividades Deportivas y Recreativas", "Formación Ciudadana", "Gramática y Lectura", "Introducción a la Ingeniería de Sistemas", "Introducción a la Matemática", "Introducción a la Tecnología de la Información", "Técnicas de Estudio"),
                    listOf("Álgebra Discreta", "Ambiente Sostenible", "Dibujo I", "Geometría Analítica", "Identidad Regional", "Matemática I", "Química General"),
                    listOf("Álgebra Lineal", "Dibujo II", "Física I", "Inglés Instrumental I", "Matemática II", "Sociohumanística", "Técnicas de Programación"),
                    listOf("Dinámica del Desarrollo", "Física II", "Inglés Instrumental II", "Laboratorio de Física I", "Matemática III", "Programación I", "Sociohumanística"),
                    listOf("Circuitos Eléctricos", "Estructura de Datos", "Inglés Instrumental III", "Matemática IV", "Mecánica Racional", "Programación II"),
                    listOf("Base de Datos I", "Electrónica", "Estadística y Probabilidades", "Programación III", "Sistemas I", "Sociohumanística"),
                    listOf("Base de Datos II", "Estadística Aplicada", "Programación IV", "Sistemas Digitales", "Sistemas II"),
                    listOf("Arquitectura del Computador", "Cálculo Numérico", "Investigación de Operaciones para Ingenieros", "Mecánica de Fluidos", "Sistemas III"),
                    listOf("Electiva", "Ingeniería Económica", "Metodología de la Investigación", "Sistemas de Controles", "Sistemas de Información Gerencial", "Sistemas Operativos"),
                    listOf("Electiva", "Herramientas Gerenciales", "Metodología de Desarrollo de Software", "Redes de Datos", "Sistemas de Señales", "Seminario Metodológico de Investigación"),
                    listOf("Evaluación y Control de Proyectos", "Electiva", "Introducción a las Telecomunicaciones", "Práctica Profesional", "Trabajo de Investigación I"),
                    listOf("Auditoría de Sistemas", "Ética y Deontología Profesional", "Simulación y Optimización", "Trabajo de Investigación II")
                )
            )
        ),
        Career(
            id = "industrial",
            name = "Ingeniería Industrial",
            degree = "Ingeniero Industrial",
            duration = "12 Trimestres",
            description = "Desarrollamos profesionales capaces de optimizar sistemas productivos, logísticos y de servicios, integrando recursos humanos, tecnológicos y financieros para elevar la competitividad de las organizaciones.",
            semesters = createSemesters(
                listOf(
                    listOf("Actividades Deportivas y Recreativas", "Comprensión y Expresión Lingüística", "Formación Ciudadana", "Introducción a la Ingeniería Industrial", "Introducción a la Matemática", "Introducción a la Tecnología de la Información", "Técnicas de Estudio"),
                    listOf("Álgebra Discreta", "Ambiente Sostenible", "Dibujo I", "Geometría Analítica", "Identidad Regional", "Matemática I", "Química General"),
                    listOf("Álgebra Lineal", "Dibujo Industrial", "Física I + Laboratorio", "Inglés Instrumental I", "Matemática II", "Sociohumanística"),
                    listOf("Dinámica del Desarrollo", "Electiva Socio Humanística", "Física II + Laboratorio", "Inglés Instrumental II", "Matemática III", "Mecánica Racional", "Programación I"),
                    listOf("Electrotecnia + Laboratorio", "Estadística y Probabilidades", "Inglés Instrumental III", "Matemática IV", "Mecánica de Fluidos", "Socio Humanística"),
                    listOf("Conversión de Energía I + Laboratorio", "Estadística Aplicada", "Gestión Tecnológica", "Técnicas Digitales", "Tecnología de los Materiales"),
                    listOf("Cálculo Numérico", "Conversión de Energía II + Laboratorio", "Fundamentos de Diseño Mecánico", "Investigación de Operaciones para Ingenieros", "Termodinámica"),
                    listOf("Control de la Calidad", "Control de Producción I", "Planificación y Controles de Procesos", "Procesos Industriales Administrativos", "Procesos Manufactura"),
                    listOf("Control de Producción II", "Dirección Industrial", "Ingeniería Económica", "Ingeniería de Métodos", "Metodología de la Investigación"),
                    listOf("Diseño de Plantas + Laboratorio", "Electiva Profesional", "Gerencia Empresarial", "Práctica Profesional", "Seminario Metodológico de Investigación"),
                    listOf("Administración de Personal", "Evaluación y Control de Proyectos", "Electiva Profesional", "Manejo de Materiales", "Trabajo de Investigación I"),
                    listOf("Ética y Deontología Profesional", "Electiva Profesional", "Higiene y Seguridad Industrial", "Trabajo de Investigación II")
                )
            )
        ),
        Career(
            id = "turismo",
            name = "Turismo",
            degree = "Licenciado en Turismo",
            duration = "12 Trimestres",
            description = "Formamos líderes en la planificación, gestión y comercialización del sector turístico, hotelero y de hospitalidad, con visión sustentable y de calidad internacional.",
            semesters = createSemesters(
                listOf(
                    listOf("Comprensión y Expresión Lingüística", "Formación Ciudadana", "Técnicas de Estudio", "Introducción a la Matemática", "Introducción a la Tecnología de la Información", "Actividades Deportivas y Recreativas", "Introducción al Turismo"),
                    listOf("Gramática Española", "Identidad Regional", "Sociología del Turismo", "Ambiente Sostenible", "Matemática Financiera", "Informática Aplicada I"),
                    listOf("Geografía Turística", "Inglés Instrumental I", "Contabilidad Aplicada al Turismo", "Informática Aplicada II", "Francés Instrumental I", "Sociohumanística"),
                    listOf("Dinámica del Desarrollo", "Turismo Nacional", "Inglés Instrumental II", "Psicología del Turismo", "Francés Instrumental II", "Sociohumanística"),
                    listOf("Turismo Regional", "Inglés Instrumental III", "Patrimonio Cultural y Turismo", "Francés Instrumental III", "Sociohumanística"),
                    listOf("Etnoturismo", "Teoría y Técnica del Turismo", "Inglés Instrumental IV", "Sistemas Turísticos", "Francés Instrumental IV"),
                    listOf("Estadística General", "Circuitos y Paquetes Turísticos", "Administración de Empresas Turísticas", "Inglés Instrumental V", "Planificación Turística"),
                    listOf("Organización de Eventos y Protocolo", "Turismo Sustentable", "Formulación de Proyectos Turísticos", "Gestión de Alojamiento", "Inglés Instrumental VI"),
                    listOf("Animación, Recreación y Esparcimiento Turístico", "Marketing Turístico Digital", "Alemán Instrumental I", "Metodología de la Investigación", "Gestión de la Calidad en Turismo"),
                    listOf("Práctica Profesional", "Gestión de Talento Humano", "Seminario Metodológico de Investigación", "Alemán Instrumental II", "Electiva Profesional I"),
                    listOf("Turismo Receptivo", "Legislación Turística", "Electiva Profesional II", "Trabajo de Investigación I", "Promoción y Comercialización de Productos"),
                    listOf("Trabajo de Investigación II", "Turismo Alternativo", "Ética y Deontología Profesional", "Electiva Profesional III")
                ), "Trimestre"
            )
        ),
        Career(
            id = "psicologia",
            name = "Psicología",
            degree = "Licenciado en Psicología Mención Intervención Social",
            duration = "12 Trimestres",
            description = "Preparamos psicólogos capacitados para comprender la conducta humana y diseñar programas de intervención comunitaria y psicosocial que promuevan el bienestar colectivo.",
            semesters = createSemesters(
                listOf(
                    listOf("Actividades Deportivas y Recreativas", "Formación Ciudadana", "Gramática y Lectura", "Introducción a la Matemática", "Introducción a la Psicología", "Introducción a la Tecnología de la Información", "Técnicas de Estudio"),
                    listOf("Ambiente Sostenible", "Estadística I", "Identidad Regional", "Psicología General I", "Teoría del Desarrollo Social"),
                    listOf("Estadística II", "Inglés Instrumental I", "Neuroanatomía I", "Psicología General II", "Psicología Social I", "Sociohumanística"),
                    listOf("Bioestadística", "Dinámica del Desarrollo", "Inglés Instrumental II", "Neurofisiología", "Psicología General III", "Psicología Social II", "Sociohumanística"),
                    listOf("Inglés Instrumental III", "Neurofisiopatología", "Psicología del Desarrollo Humano I", "Psicología de Intervención Comunitaria", "Psicología de la Personalidad I", "Sociohumanística"),
                    listOf("Psicología del Desarrollo Humano II", "Psicología del Aprendizaje", "Psicopatología", "Psicometría I", "Psicología de la Personalidad II"),
                    listOf("Psicología Experimental I", "Psicología Clínica", "Psicología del Deporte", "Psicometría II", "Psicología Socioeducativa"),
                    listOf("Criminología", "Desarrollo Social Contemporáneo", "Psicología Clínica Infanto-Juvenil", "Psicología Experimental II", "Salud Laboral", "Técnicas de Entrevistas"),
                    listOf("Evaluación Psicológica I", "Metodología de la Investigación", "Psicología Clínica del Adulto", "Psicología Jurídica", "Telepsicología", "Teoría y Técnicas de Grupo I"),
                    listOf("Desarrollo Comunitario", "Primeros Auxilios Psicológicos", "Práctica Profesional Infanto-Juvenil", "Seminario Metodológico de Investigación", "Teoría y Técnicas de Grupo II"),
                    listOf("Electiva", "Electiva", "Evaluación Psicológica II", "Práctica Profesional Adulto", "Trabajo de Investigación I"),
                    listOf("Ética y Deontología Profesional", "Electiva", "Práctica Profesional Comunitaria", "Trabajo de Investigación II")
                )
            )
        ),
        Career(
            id = "comunicacion",
            name = "Comunicación Social",
            degree = "Licenciado en Comunicación Social Mención Empresarial",
            duration = "12 Trimestres",
            description = "Formamos profesionales de la comunicación con destrezas periodísticas, audiovisuales y corporativas, comprometidos con la verdad, la ética y la innovación digital.",
            semesters = createSemesters(
                listOf(
                    listOf("Actividades Deportivas y Recreativas", "Comprensión y Expresión Lingüística", "Formación Ciudadana", "Introducción a la Ciencia de la Comunicación", "Introducción a la Matemática", "Introducción a la Tecnología de la Información", "Técnicas de Estudio"),
                    listOf("Ambiente Sostenible", "Arte y Comunicación", "Historia de la Comunicación I", "Identidad Regional", "Informática I", "Taller de Redacción I"),
                    listOf("Electiva Socio Humanística", "Historia Contemporánea de América", "Historia de la Comunicación II", "Inglés Instrumental I", "Psicología de la Comunicación", "Taller de Redacción II", "Tecnología y Comunicación"),
                    listOf("Artes Gráficas y Comunicación", "Dinámica del Desarrollo", "Electiva Socio Humanística", "Hist. Contemp. de Venezuela en los Medios", "Inglés Instrumental II", "Taller de Expresión Oral I", "Teoría de la Información"),
                    listOf("Electiva Socio Humanística", "Inglés Instrumental III", "Manejo de Crisis", "Periodismo I", "Radio", "Semiótica", "Taller de Expresión Oral II"),
                    listOf("Fotografía I", "Lenguaje y Comunicación Visual", "Periodismo II", "Televisión", "Teoría de la Opinión Pública"),
                    listOf("Comunicación y Desarrollo", "Estrategias Comunicacionales", "Fotografía II", "Periodismo III", "Socio Política de la Comunicación", "Teoría y Práctica de la Argumentación"),
                    listOf("Cine", "Comunicación Internacional", "Estadística General", "Marketing Digital", "Relaciones Públicas"),
                    listOf("Administración Empresarial", "Comunicación Social en las Organizaciones", "Metodología de la Investigación", "Neuromarketing", "Responsabilidad Social Empresarial"),
                    listOf("Diagnóstico de Comunicación", "Electiva Profesional", "Gerencia Empresarial y Comunicación", "Práctica Profesional I", "Seminario Metodológico de Investigación"),
                    listOf("Electiva Profesional", "Planificación de Eventos", "Práctica Profesional II", "Taller de Liderazgo y Consultoría", "Trabajo de Investigación I"),
                    listOf("Ética y Deontología Profesional", "Electiva Profesional", "Taller de Evaluación del Entorno", "Trabajo de Investigación II")
                )
            )
        ),
        Career(
            id = "derecho",
            name = "Derecho",
            degree = "Abogado",
            duration = "12 Trimestres",
            description = "Preparamos juristas con alto sentido ético, destrezas de litigación oral y profundo conocimiento de la legislación nacional e internacional. Capaces de resolver conflictos sociales y defender los derechos individuales con justicia y equidad.",
            semesters = createSemesters(
                listOf(
                    listOf("Actividades Deportivas y Recreativas", "Formación Ciudadana", "Gramática y Lectura", "Historia del Derecho", "Introducción a la Matemática", "Introducción a la Tecnología de la Información", "Técnicas de Estudio"),
                    listOf("Ambiente Sostenible", "Economía Política", "Filosofía del Derecho", "Identidad Regional", "Introducción al Derecho", "Inglés Instrumental I", "Sociología Jurídica"),
                    listOf("Derecho Constitucional I", "Derechos Humanos", "Derecho Romano I", "Electiva Socio Humanística", "Fundamentos de Estadística", "Introducción a la Lógica Jurídica", "Inglés Instrumental II"),
                    listOf("Dinámica del Desarrollo", "Derecho Constitucional II", "Derecho Romano II", "Derecho Internacional Público", "Electiva Socio Humanística", "Inglés Instrumental III"),
                    listOf("Derecho Administrativo I", "Derecho Civil I", "Derecho Mercantil", "Derecho Penal I", "Derecho Internacional Privado", "Electiva Socio Humanística"),
                    listOf("Derecho Administrativo II", "Derecho Civil II", "Derecho de Familia", "Derecho Penal II", "Derecho del Trabajo", "Teoría General de la Prueba I"),
                    listOf("Derecho Penal III", "Derecho Tributario", "Obligaciones I", "Teoría General de la Prueba II", "Teoría del Proceso", "Violencia de Género"),
                    listOf("Derecho Contencioso Administrativo I", "Derecho Procesal Penal I", "Derecho Procesal del Trabajo", "Electiva Profesional", "Medicina Legal", "Obligaciones II"),
                    listOf("Contratos y Garantías", "Derecho Contencioso Administrativo II", "Derecho de Sucesiones", "Derecho Procesal Penal II", "Metodología de la Investigación", "Redacción Jurídica"),
                    listOf("Derecho Procesal Civil I", "Derecho Procesal Tributario", "Ética Pública", "Práctica Profesional", "Seminario Metodológico de Investigación"),
                    listOf("Derecho Procesal Civil II", "Ética y Deontología Profesional", "Electiva Profesional", "Oratoria Jurídica", "Trabajo de Investigación I"),
                    listOf("Electiva Profesional", "Medios Alternos de Resolución de Conflictos", "Procedimientos Constitucionales", "Procesos Especiales", "Trabajo de Investigación II")
                )
            )
        ),
        Career(
            id = "contaduria",
            name = "Contaduría Pública",
            degree = "Licenciado en Contaduría Pública",
            duration = "12 Trimestres",
            description = "Formamos expertos financieros capacitados para diseñar sistemas de información contable, auditar estados financieros, asesorar tributariamente y tomar decisiones estratégicas de inversión bajo estándares globales (NIIF).",
            semesters = createSemesters(
                listOf(
                    listOf("Actividades Deportivas y Recreativas", "Formación Ciudadana", "Gramática y Lectura", "Intro. Ciencias Económicas, Admin. y Contables", "Introducción a la Matemática", "Introducción a la Tecnología de la Información", "Técnicas de Estudio"),
                    listOf("Administración I", "Ambiente Sostenible", "Contabilidad I", "Identidad Regional", "Inglés Instrumental I", "Matemática I"),
                    listOf("Administración II", "Contabilidad II", "Electiva Socio Humanística", "Fundamentos del Derecho", "Inglés Instrumental II", "Matemática II", "Microeconomía"),
                    listOf("Contabilidad III", "Dinámica del Desarrollo", "Derecho Mercantil", "Electiva Socio Humanística", "Estadística y Probabilidades", "Macroeconomía", "Matemática Financiera I"),
                    listOf("Banca y Seguro", "Contabilidad IV", "Comportamiento Organizacional", "Derecho Laboral", "Electiva Socio Humanística", "Estadística Aplicada", "Matemática Financiera II"),
                    listOf("Administración Pública", "Auditoría I", "Contabilidad de Criptoactivos", "Contabilidad de Costos I", "Normas Internacionales de Información Financiera", "Sistemas y Procedimientos Contables"),
                    listOf("Ajuste por Inflación", "Auditoría II", "Contabilidad Aplicada I", "Contabilidad de Costos II", "Finanzas I", "Legislación Fiscal"),
                    listOf("Auditoría III", "Contabilidad Aplicada II", "Contabilidad de Costos III", "Finanzas II", "Finanzas Públicas", "Impuesto sobre la Renta I"),
                    listOf("Análisis de Estados Financieros I", "Impuesto sobre la Renta II", "Metodología de la Investigación", "Presupuesto Corporativo", "Taller de Auditoría"),
                    listOf("Análisis de Estados Financieros II", "Electiva Profesional", "Planificación Tributaria", "Práctica Profesional", "Seminario Metodológico de Investigación"),
                    listOf("Auditoría Aplicada", "Electiva Profesional", "Presupuesto Público", "Trabajo de Investigación I"),
                    listOf("Contabilidad Gubernamental", "Ética y Deontología Profesional", "Electiva Profesional", "Trabajo de Investigación II")
                )
            )
        ),
        Career(
            id = "administracion",
            name = "Administración",
            degree = "Licenciado en Administración",
            duration = "12 Trimestres",
            description = "Desarrollamos líderes empresariales con visión global, capaces de planificar, organizar, dirigir y controlar organizaciones de cualquier índole, maximizando su valor de manera sustentable e innovadora.",
            semesters = createSemesters(
                listOf(
                    listOf("Actividades Deportivas y Recreativas", "Formación Ciudadana", "Gramática y Lectura", "Intro. Ciencias Económicas, Admin. y Contables", "Introducción a la Matemática", "Introducción a la Tecnología de la Información", "Técnicas de Estudio"),
                    listOf("Administración I", "Ambiente Sostenible", "Contabilidad I", "Identidad Regional", "Inglés Instrumental I", "Matemática I"),
                    listOf("Administración II", "Contabilidad II", "Electiva Socio Humanística", "Fundamentos del Derecho", "Inglés Instrumental II", "Matemática II", "Microeconomía"),
                    listOf("Contabilidad III", "Dinámica del Desarrollo", "Derecho Mercantil", "Electiva Socio Humanística", "Estadística y Probabilidades", "Macroeconomía", "Matemática Financiera I"),
                    listOf("Banca y Seguro", "Contabilidad IV", "Comportamiento Organizacional", "Derecho Laboral", "Electiva Socio Humanística", "Estadística Aplicada", "Matemática Financiera II"),
                    listOf("Administración Pública", "Contabilidad de Costos I", "Fundamentos de Mercado", "Gestión del Capital Humano", "Investigación de Operaciones", "Normas Internacionales de Información Financiera"),
                    listOf("Ajuste por Inflación", "Contabilidad Gerencial", "Finanzas I", "Investigación de Mercados", "Legislación Fiscal", "Organización y Métodos"),
                    listOf("Administración de Ventas", "Finanzas II", "Finanzas Públicas", "Impuesto sobre la Renta I", "Planificación y Gerencia Estratégica"),
                    listOf("Análisis de Estados Financieros I", "Emprendimiento", "Metodología de la Investigación", "Presupuesto Corporativo", "Sistemas de Información y Procesos Gerenciales"),
                    listOf("Análisis de Problemas y Toma de Decisiones", "Electiva Profesional", "Formulación y Evaluación de Proyectos", "Práctica Profesional", "Seminario Metodológico de Investigación"),
                    listOf("Administración de Operaciones", "Electiva Profesional", "Presupuesto Público", "Responsabilidad Social Empresarial", "Trabajo de Investigación I"),
                    listOf("Ética y Deontología Profesional", "Electiva Profesional", "Trabajo de Investigación II")
                )
            )
        ),
        Career(
            id = "educacion",
            name = "Educación Integral",
            degree = "Licenciado en Educación Integral",
            duration = "12 Trimestres",
            description = "Formamos educadores con sólida formación pedagógica, científica y humanística, capaces de liderar procesos de enseñanza-aprendizaje transformadores en la educación básica.",
            semesters = createSemesters(
                listOf(
                    listOf("Gramática y Lectura", "Formación Ciudadana", "Técnicas de Estudio", "Introducción a la Matemática", "Fundamentos de la Educación", "Actividades Deportivas y Recreativas", "Introducción a la Tecnología de la Información"),
                    listOf("Lengua Española I", "Psicología General", "Ambiente Sostenible", "Corrientes del Pensamiento Pedagógico", "Matemática I", "Identidad Regional", "Educación Básica"),
                    listOf("Lengua Española II", "Desarrollo de Procesos Cognoscitivos", "Currículum", "Matemática II", "Inglés Instrumental I", "Historia Universal", "Electiva Socio Humanística"),
                    listOf("Lectoescritura", "Psicología del Aprendizaje", "Dinámica del Desarrollo", "Didáctica General", "Geometría", "Inglés Instrumental II", "Electiva Sociohumanística"),
                    listOf("Literatura Hispanoamericana", "Didáctica de Procesos", "Dificultades del Aprendizaje", "Inglés Instrumental III", "Historia de Venezuela", "Electiva Socio Humanística"),
                    listOf("Literatura Venezolana", "Ciencias I", "Planificación Educativa", "Venezuela Contemporánea", "Pensamiento Bolivariano", "Geografía General"),
                    listOf("Literatura Infantil", "Ciencias II", "Estrategias y Recursos de Aprendizaje", "Estadística General", "Geografía de Venezuela"),
                    listOf("Práctica Profesional I", "Evaluación Educativa", "Estadística Aplicada", "Educación para el Trabajo", "Técnicas de Grupo Aplicadas a la Educación"),
                    listOf("Artes Plásticas", "Práctica Profesional II", "Gerencia Educativa", "Metodología de la Investigación", "Entornos Virtuales de Aprendizaje"),
                    listOf("Práctica Profesional III", "Medios Digitales para la Educación", "Música y Artes Escénicas", "Electiva Profesional"),
                    listOf("Práctica Profesional IV", "Producción de Medios Digitales", "Electiva Profesional"),
                    listOf("Ética y Deontología Profesional", "Electiva Profesional", "Práctica Profesional V")
                ), "Trimestre"
            )
        ),
        Career(
            id = "idiomas",
            name = "Idiomas Modernos",
            degree = "Licenciado en Idiomas Modernos",
            duration = "12 Trimestres",
            description = "Capacitamos profesionales con dominio bilingüe fluido (Inglés-Francés) idóneos para la traducción, interpretación, relaciones públicas internacionales, turismo de negocios y enlace cultural global.",
            semesters = createSemesters(
                listOf(
                    listOf("Actividades Deportivas y Recreativas", "Formación Ciudadana", "Gramática y Lectura", "Introducción a los Idiomas Modernos", "Introducción a la Matemática", "Introducción a la Tecnología de la Información", "Técnicas de Estudio"),
                    listOf("Ambiente Sostenible", "Fonética y Fonología del Español", "Francés I", "Gramática Española", "Identidad Regional", "Inglés I"),
                    listOf("Electiva Socio Humanística", "Fonética y Fonología del Francés", "Fonética y Fonología del Inglés", "Francés II", "Inglés II", "Lectura y Escritura del Español", "Psicolingüística"),
                    listOf("Composición y Estilo del Español", "Dinámica del Desarrollo", "Electiva Socio Humanística", "Francés III", "Inglés III", "Sintaxis y Morfología del Francés", "Sintaxis y Morfología del Inglés"),
                    listOf("Electiva Socio Humanística", "Francés IV", "Historia y Literatura Hispanoamericana", "Inglés IV", "Lectura y Escritura en Francés", "Lectura y Escritura en Inglés"),
                    listOf("Francés V", "Historia y Literatura Francesa", "Historia y Literatura Inglesa", "Inglés V", "Italiano I", "Lingüística General"),
                    listOf("Fonética y Fonología del Italiano", "Francés VI", "Historia y Literatura Estadounidense", "Inglés VI", "Italiano II"),
                    listOf("Análisis de Textos Literarios en Inglés", "Fundamentos de Estadística", "Historia y Literatura Francófonas", "Italiano III", "Sintaxis y Morfología del Italiano"),
                    listOf("Análisis de Textos Literarios en Francés", "Italiano IV", "Introducción a la Traducción e Interpretación", "Lectura y Escritura del Italiano", "Metodología de la Investigación"),
                    listOf("Electiva Profesional", "Historia y Literatura Italiana", "Italiano V", "Práctica Profesional", "Seminario Metodológico de Investigación"),
                    listOf("Ética y Deontología Profesional", "Electiva Profesional", "Italiano VI", "Técnica de Traducción", "Trabajo de Investigación I"),
                    listOf("Análisis de Textos Literarios en Italiano", "Electiva Profesional", "Trabajo de Investigación II")
                )
            )
        ),
        Career(
            id = "diseno",
            name = "Artes Mención Diseño Gráfico",
            degree = "Licenciado en Artes Mención Diseño Gráfico",
            duration = "12 Trimestres",
            description = "Incentivamos la creatividad visual y conceptual para formar diseñadores gráficos capaces de liderar agencias creativas, crear identidades de marca, maquetar interfaces digitales y diseñar piezas publicitarias cautivadoras.",
            semesters = createSemesters(
                listOf(
                    listOf("Actividades Deportivas y Recreativas", "Formación Ciudadana", "Gramática y Lectura", "Introducción a las Artes Plásticas", "Introducción a la Matemática", "Introducción a la Tecnología de la Información", "Técnicas de Estudio"),
                    listOf("Ambiente Sostenible", "Geometría Aplicada", "Introducción al Diseño Gráfico", "Identidad Regional", "Teoría de la Forma y el Color I", "Tipografía"),
                    listOf("Dibujo I", "Diseño Gráfico I", "Expresión Gráfica I", "Inglés Instrumental I", "Semiología de la Imagen", "Sociohumanística", "Teoría de la Forma y el Color II"),
                    listOf("Dinámica del Desarrollo", "Dibujo II", "Diseño Gráfico II", "Expresión Gráfica II", "Historia del Arte y Diseño", "Inglés Instrumental II", "Sociohumanística"),
                    listOf("Arte y Diseño Tridimensional", "Dibujo Anatómico", "Diseño Gráfico III", "Inglés Instrumental III", "Matemática Aplicada", "Sociología del Arte y del Diseño", "Sociohumanística"),
                    listOf("Arte y Diseño Contemporáneo", "Dibujo de Animación", "Diseño Gráfico IV", "Fotografía I", "Fundamentos de Mercado", "Psicología de la Creatividad", "Representación Gráfica I"),
                    listOf("Arte y Diseño Contemporáneo Venezolano", "Diseño Gráfico V", "Fotografía II", "Ilustración", "Publicidad y Mercadeo I", "Representación Gráfica II"),
                    listOf("Fundamentos de Estadística", "Introducción al Diseño Web", "Producción Audiovisual I", "Publicidad y Mercadeo II", "Representación Gráfica III", "Señalética"),
                    listOf("Diseño Web", "Electiva", "Metodología de la Investigación", "Producción Audiovisual II", "Sistemas de Impresión I"),
                    listOf("Electiva", "Práctica Profesional", "Sistemas de Impresión II", "Seminario Metodológico de Investigación"),
                    listOf("Diseño Arquitectónico", "Diseño de Exteriores", "Electiva", "Taller de Diseño Publicitario", "Trabajo de Investigación I"),
                    listOf("Ética y Deontología Profesional", "Taller de Diseño Digital", "Trabajo de Investigación II")
                )
            )
        )
    )

    val campusLocations = listOf(
        CampusLocation(
            id = 1,
            name = "Edificio de Aulas (Rectorado)",
            description = "El corazón administrativo y académico de UNIMAR, con su hermosa arquitectura colonial con techos de tejas de barro y amplios pasillos frescos que invitan al estudio y la convivencia.",
            category = "Académico",
            imageDescription = "Un edificio colonial blanco con tejas anaranjadas y vigas de madera azul, rodeado de palmeras."
        ),
        CampusLocation(
            id = 2,
            name = "Plaza de las Banderas",
            description = "Punto de encuentro central y emblemático de la universidad, donde ondean las banderas institucional, regional y nacional. Sede frecuente de ferias, actividades estudiantiles y actos solemnes al aire libre.",
            category = "Área Social",
            imageDescription = "Una plaza abierta pavimentada rodeada de áreas verdes, con múltiples mástiles de banderas."
        ),
        CampusLocation(
            id = 3,
            name = "Biblioteca Dr. Efraín Subero",
            description = "Espacio silencioso e idóneo para la investigación intelectual y el estudio concentrado, que cuenta con una amplia colección bibliográfica, áreas digitales de consulta y salas de lectura grupal.",
            category = "Estudio",
            imageDescription = "Un salón luminoso con estantes llenos de libros de colores y mesas de estudio de madera."
        ),
        CampusLocation(
            id = 4,
            name = "Aula Magna",
            description = "El auditorio principal de UNIMAR, escenario de grandes ceremonias académicas, conferencias magistrales, asambleas estudiantiles e importantes eventos artísticos y culturales de Margarita.",
            category = "Eventos",
            imageDescription = "Un auditorio con un gran escenario de madera, asientos ordenados de color azul y excelente iluminación."
        ),
        CampusLocation(
            id = 5,
            name = "Pasillos Coloniales y Jardines",
            description = "Hermosas áreas ornamentales repletas de frondosas palmeras, flores caribeñas y bancos de descanso, perfectas para relajarse entre clases bajo la refrescante brisa marina de la isla.",
            category = "Naturaleza",
            imageDescription = "Un pasillo porticado colonial con arcos blancos, suelo de terracota y jardines verdes con flores rojas al lado."
        ),
        CampusLocation(
            id = 6,
            name = "Complejo Deportivo",
            description = "Cancha de usos múltiples techada que promueve la salud de la comunidad unamarista mediante torneos inter-carreras de baloncesto, fútbol sala y voleibol, además de servir de entrenamiento para los equipos oficiales.",
            category = "Deporte",
            imageDescription = "Una cancha deportiva de suelo azul pulido con canastas de baloncesto, porterías y gradas laterales."
        )
    )
}
