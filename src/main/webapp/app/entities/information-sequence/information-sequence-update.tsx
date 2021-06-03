import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './information-sequence.reducer';
import { IInformationSequence } from 'app/shared/model/information-sequence.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInformationSequenceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InformationSequenceUpdate = (props: IInformationSequenceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { informationSequenceEntity, items, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/information-sequence');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...informationSequenceEntity,
        ...values,
        item: items.find(it => it.id.toString() === values.itemId.toString()),
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
          <h2 id="hcpNphiesPortalApp.informationSequence.home.createOrEditLabel" data-cy="InformationSequenceCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.informationSequence.home.createOrEditLabel">
              Create or edit a InformationSequence
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : informationSequenceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="information-sequence-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="information-sequence-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="infSeqLabel" for="information-sequence-infSeq">
                  <Translate contentKey="hcpNphiesPortalApp.informationSequence.infSeq">Inf Seq</Translate>
                </Label>
                <AvField id="information-sequence-infSeq" data-cy="infSeq" type="string" className="form-control" name="infSeq" />
              </AvGroup>
              <AvGroup>
                <Label for="information-sequence-item">
                  <Translate contentKey="hcpNphiesPortalApp.informationSequence.item">Item</Translate>
                </Label>
                <AvInput id="information-sequence-item" data-cy="item" type="select" className="form-control" name="itemId">
                  <option value="" key="0" />
                  {items
                    ? items.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/information-sequence" replace color="info">
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
  items: storeState.item.entities,
  informationSequenceEntity: storeState.informationSequence.entity,
  loading: storeState.informationSequence.loading,
  updating: storeState.informationSequence.updating,
  updateSuccess: storeState.informationSequence.updateSuccess,
});

const mapDispatchToProps = {
  getItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InformationSequenceUpdate);
