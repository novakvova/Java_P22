import { useState, useRef, useEffect } from "react";

interface Option {
    value: number | string;
    label: string;
    image?: string;
}

interface Props {
    // name: string;
    label: string;
    options: Option[];
    value?: string | number;
    onChange?: (value: string | number) => void;
}

const SelectField: React.FC<Props> = ({
                                          // name,
                                          label,
                                          options,
                                          value,
                                          onChange
                                      }) => {
    const [open, setOpen] = useState(false);
    const ref = useRef<HTMLDivElement>(null);

    const selected = options.find((o) => o.value === value);

    useEffect(() => {
        const handleClick = (e: MouseEvent) => {
            if (ref.current && !ref.current.contains(e.target as Node)) {
                setOpen(false);
            }
        };
        document.addEventListener("click", handleClick);
        return () => document.removeEventListener("click", handleClick);
    }, []);

    return (
        <div className="w-full mb-5" ref={ref}>
            <label className="block mb-2 text-sm font-medium">
                {label}
            </label>

            <div className="relative">
                <button
                    type="button"
                    onClick={() => setOpen(!open)}
                    className="w-full border px-3 py-2 rounded-lg
                               flex items-center justify-between shadow-sm
                               transition"
                >
                    {selected ? (
                        <div className="flex items-center gap-2">
                            {selected.image && (
                                <img
                                    src={selected.image}
                                    alt={selected.label}
                                    className="w-6 h-4 object-cover rounded-sm"
                                />
                            )}
                            <span className="text-sm ">{selected.label}</span>
                        </div>
                    ) : (
                        <span className=" text-sm">Оберіть країну...</span>
                    )}

                    <svg
                        className={`w-4 h-4  transition-transform ${
                            open ? "rotate-180" : ""
                        }`}
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                    >
                        <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth={2}
                            d="M19 9l-7 7-7-7"
                        />
                    </svg>
                </button>

                {open && (
                    <div
                        className="absolute w-full border mt-1 rounded-lg
                   shadow-lg max-h-60 overflow-auto z-50 animate-fadeIn"
                        style={{
                            backgroundColor: "var(--bg)",
                            color: "var(--fg)",
                            borderColor: "var(--fg)"
                        }}
                    >
                        {options.map((opt) => (
                            <div
                                key={opt.value}
                                onClick={() => {
                                    onChange?.(opt.value);
                                    setOpen(false);
                                }}
                                className="flex items-center gap-2 px-3 py-2 cursor-pointer transition hover:opacity-80"
                                style={{
                                    backgroundColor: "var(--bg)",
                                    color: "var(--fg)"
                                }}
                            >
                                {opt.image && (
                                    <img
                                        src={opt.image}
                                        alt={opt.label}
                                        className="w-6 h-4 object-cover rounded-sm"
                                    />
                                )}
                                <span className="text-sm">{opt.label}</span>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};

export default SelectField;
