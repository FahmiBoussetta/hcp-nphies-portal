import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAdjudicationSubDetailItem } from 'app/shared/model/adjudication-sub-detail-item.model';
import { getEntities as getAdjudicationSubDetailItems } from 'app/entities/adjudication-sub-detail-item/adjudication-sub-detail-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adjudication-sub-detail-notes.reducer';
import { IAdjudicationSubDetailNotes } from 'app/shared/model/adjudication-sub-detail-notes.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAdjudicationSubDetailNotesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdjudicationSubDetailNotesUpdate = (props: IAdjudicationSubDetailNotesUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { adjudicationSubDetailNotesEntity, adjudicationSubDetailItems, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/adjudication-sub-detail-notes');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAdjudicationSubDetailItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...adjudicationSubDetailNotesEntity,
        ...values,
        adjudicationSubDetailItem: adjudicationSubDetailItems.find(
          it => it.id.toString() === values.adjudicationSubDetailItemId.toString()
        ),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="hcpNphiesPortalApp.adjudicationSubDetailNotes.home.createOrEditLabel"
            data-cy="AdjudicationSubDetailNotesCreateUpdateHeading"
          >
            <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailNotes.home.createOrEditLabel">
              Create or edit a AdjudicationSubDetailNotes
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : adjudicationSubDetailNotesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="adjudication-sub-detail-notes-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="adjudication-sub-detail-notes-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="noteLabel" for="adjudication-sub-detail-notes-note">
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailNotes.note">Note</Translate>
                </Label>
                <AvField id="adjudication-sub-detail-notes-note" data-cy="note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label for="adjudication-sub-detail-notes-adjudicationSubDetailItem">
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailNotes.adjudicationSubDetailItem">
                    Adjudication Sub Detail Item
                  </Translate>
                </Label>
                <AvInput
                  id="adjudication-sub-detail-notes-adjudicationSubDetailItem"
                  data-cy="adjudicationSubDetailItem"
                  type="select"
                  className="form-control"
                  name="adjudicationSubDetailItemId"
                >
                  <option value="" key="0" />
                  {adjudicationSubDetailItems
                    ? adjudicationSubDetailItems.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/adjudication-sub-detail-notes" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  adjudicationSubDetailItems: storeState.adjudicationSubDetailItem.entities,
  adjudicationSubDetailNotesEntity: storeState.adjudicationSubDetailNotes.entity,
  loading: storeState.adjudicationSubDetailNotes.loading,
  updating: storeState.adjudicationSubDetailNotes.updating,
  updateSuccess: storeState.adjudicationSubDetailNotes.updateSuccess,
});

const mapDispatchToProps = {
  getAdjudicationSubDetailItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjudicationSubDetailNotesUpdate);
