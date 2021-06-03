import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdjudicationDetailNotes, defaultValue } from 'app/shared/model/adjudication-detail-notes.model';

export const ACTION_TYPES = {
  FETCH_ADJUDICATIONDETAILNOTES_LIST: 'adjudicationDetailNotes/FETCH_ADJUDICATIONDETAILNOTES_LIST',
  FETCH_ADJUDICATIONDETAILNOTES: 'adjudicationDetailNotes/FETCH_ADJUDICATIONDETAILNOTES',
  CREATE_ADJUDICATIONDETAILNOTES: 'adjudicationDetailNotes/CREATE_ADJUDICATIONDETAILNOTES',
  UPDATE_ADJUDICATIONDETAILNOTES: 'adjudicationDetailNotes/UPDATE_ADJUDICATIONDETAILNOTES',
  PARTIAL_UPDATE_ADJUDICATIONDETAILNOTES: 'adjudicationDetailNotes/PARTIAL_UPDATE_ADJUDICATIONDETAILNOTES',
  DELETE_ADJUDICATIONDETAILNOTES: 'adjudicationDetailNotes/DELETE_ADJUDICATIONDETAILNOTES',
  RESET: 'adjudicationDetailNotes/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdjudicationDetailNotes>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AdjudicationDetailNotesState = Readonly<typeof initialState>;

// Reducer

export default (state: AdjudicationDetailNotesState = initialState, action): AdjudicationDetailNotesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATIONDETAILNOTES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATIONDETAILNOTES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADJUDICATIONDETAILNOTES):
    case REQUEST(ACTION_TYPES.UPDATE_ADJUDICATIONDETAILNOTES):
    case REQUEST(ACTION_TYPES.DELETE_ADJUDICATIONDETAILNOTES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONDETAILNOTES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATIONDETAILNOTES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATIONDETAILNOTES):
    case FAILURE(ACTION_TYPES.CREATE_ADJUDICATIONDETAILNOTES):
    case FAILURE(ACTION_TYPES.UPDATE_ADJUDICATIONDETAILNOTES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONDETAILNOTES):
    case FAILURE(ACTION_TYPES.DELETE_ADJUDICATIONDETAILNOTES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATIONDETAILNOTES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATIONDETAILNOTES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADJUDICATIONDETAILNOTES):
    case SUCCESS(ACTION_TYPES.UPDATE_ADJUDICATIONDETAILNOTES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONDETAILNOTES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADJUDICATIONDETAILNOTES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/adjudication-detail-notes';

// Actions

export const getEntities: ICrudGetAllAction<IAdjudicationDetailNotes> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ADJUDICATIONDETAILNOTES_LIST,
  payload: axios.get<IAdjudicationDetailNotes>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAdjudicationDetailNotes> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADJUDICATIONDETAILNOTES,
    payload: axios.get<IAdjudicationDetailNotes>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAdjudicationDetailNotes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADJUDICATIONDETAILNOTES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdjudicationDetailNotes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADJUDICATIONDETAILNOTES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAdjudicationDetailNotes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONDETAILNOTES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdjudicationDetailNotes> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADJUDICATIONDETAILNOTES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
